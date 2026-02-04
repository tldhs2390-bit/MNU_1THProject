package com.izakaya.sion.admin.controller;

import com.izakaya.sion.entity.StoreEntity;
import com.izakaya.sion.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// ✅✅✅ [추가 import]
import com.izakaya.sion.admin.dto.AdminStoreTodayResponse;
import com.izakaya.sion.repository.PaymentRepository;
import com.izakaya.sion.repository.ReservationRepository;
import com.izakaya.sion.entity.PaymentStatus;
import com.izakaya.sion.entity.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/store")
public class AdminStoreController {

    private final StoreRepository storeRepository;

    // ✅✅✅ [추가] 실시간 집계에 필요
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    // ✅ 점포 등록/목록 페이지
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String storePage(Model model) {
        model.addAttribute("stores", storeRepository.findAllByOrderByIdAsc());
        model.addAttribute("form", new StoreForm());
        return "admin/store";
    }

    // ✅ 점포 저장(신규)
    @PostMapping("/save")
    @PreAuthorize("isAuthenticated()")
    public String save(@ModelAttribute("form") StoreForm form) {

        if (isBlank(form.getCity()) || isBlank(form.getName())) {
            return "redirect:/admin/store?error=required";
        }

        Double lat = parseDoubleOrNull(form.getLat());
        Double lng = parseDoubleOrNull(form.getLng());
        if (lat == null || lng == null) {
            return "redirect:/admin/store?error=latlng";
        }

        StoreEntity s = new StoreEntity();
        s.setCity(form.getCity().trim());
        s.setName(form.getName().trim());
        s.setLat(lat);
        s.setLng(lng);

        storeRepository.save(s);

        // ✅ 저장 후 redirect → GET에서 stores 다시 조회 → 목록에 바로 포함
        return "redirect:/admin/store?success";
    }

    // ✅ 점포 수정(업데이트)
    @PostMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public String update(@RequestParam("id") Long id,
                         @ModelAttribute("form") StoreForm form) {

        if (id == null) return "redirect:/admin/store?error=required";
        if (isBlank(form.getCity()) || isBlank(form.getName())) {
            return "redirect:/admin/store?error=required";
        }

        Double lat = parseDoubleOrNull(form.getLat());
        Double lng = parseDoubleOrNull(form.getLng());
        if (lat == null || lng == null) {
            return "redirect:/admin/store?error=latlng";
        }

        Optional<StoreEntity> opt = storeRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/admin/store?error=notfound";
        }

        StoreEntity s = opt.get();
        s.setCity(form.getCity().trim());
        s.setName(form.getName().trim());
        s.setLat(lat);
        s.setLng(lng);

        storeRepository.save(s);

        return "redirect:/admin/store?updated";
    }

    // ✅ 점포 삭제
    @PostMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable("id") Long id) {
        storeRepository.deleteById(id);
        return "redirect:/admin/store?deleted";
    }

    // ✅ /admin/store/list -> admin/store_list.html 렌더링
    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String list(Model model) {
        model.addAttribute("stores", storeRepository.findAllByOrderByIdAsc());
        return "admin/store_list";
    }

    // =========================================================
    // ✅✅✅ [추가] 지점별 "본일매출/예약건수" API
    // - 영업일 기준: 04:00 ~ 다음날 04:00
    // - store_list.html 에서 10초마다 호출하는 API
    // URL: GET /admin/store/today-sales?storeId=1
    // response: {storeId, todaySales, reservationCount}
    // =========================================================
    @GetMapping("/today-sales")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public AdminStoreTodayResponse todaySales(@RequestParam("storeId") Long storeId) {

        // ✅ storeId 없거나 잘못된 경우도 화면이 안 깨지게 0으로
        if (storeId == null) {
            return new AdminStoreTodayResponse(null, 0L, 0L);
        }

        // ✅ 영업일 범위 계산 (04:00 기준)
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        LocalDateTime today4am = LocalDateTime.of(today, LocalTime.of(4, 0));

        LocalDateTime from;
        if (now.isBefore(today4am)) {
            // 새벽 0~3:59 는 "전날 04:00 ~ 오늘 04:00" 이 영업일
            from = LocalDateTime.of(today.minusDays(1), LocalTime.of(4, 0));
        } else {
            from = today4am;
        }
        LocalDateTime to = from.plusDays(1);

        // ✅ 결제 완료 상태(PAID) 파싱 (프로젝트 enum 이름이 다를 수 있어 안전처리)
        PaymentStatus paidStatus = resolvePaymentPaidStatus();

        long todaySales = paymentRepository.sumPaidAmountByStoreAndPaidAtBetween(
                storeId,
                paidStatus,
                from,
                to
        );

        // ✅ 예약 상태(확정/승인 계열만 카운트) - enum 이름이 달라도 안전처리
        Set<ReservationStatus> okStatuses = resolveReservationCountStatuses();

        long reservationCount = 0L;
        if (!okStatuses.isEmpty()) {
            reservationCount = reservationRepository.countByStoreIdAndVisitAtBetweenAndStatusIn(
                    storeId,
                    from,
                    to,
                    okStatuses
            );
        }

        return new AdminStoreTodayResponse(storeId, todaySales, reservationCount);
    }

    // ===== 내부 유틸 =====
    private boolean isBlank(String v) {
        return v == null || v.trim().isEmpty();
    }

    private Double parseDoubleOrNull(String v) {
        try {
            if (v == null) return null;
            String t = v.trim();
            if (t.isEmpty()) return null;
            return Double.parseDouble(t);
        } catch (Exception e) {
            return null;
        }
    }

    // ✅✅✅ PAID 상태 찾기 (없으면 첫 enum)
    private PaymentStatus resolvePaymentPaidStatus() {
        try {
            return PaymentStatus.valueOf("PAID");
        } catch (Exception e) {
            // fallback: enum 첫 값
            PaymentStatus[] values = PaymentStatus.values();
            return values.length > 0 ? values[0] : null;
        }
    }

    // ✅✅✅ 예약 카운트에 포함할 status들을 안전하게 구성
    // - 프로젝트 enum이 ACCEPTED/APPROVED/CONFIRMED 등 무엇이든 대응
    private Set<ReservationStatus> resolveReservationCountStatuses() {
        Set<ReservationStatus> set = new HashSet<>();
        String[] candidates = new String[]{"ACCEPTED", "APPROVED", "CONFIRMED", "RESERVED"};
        for (String c : candidates) {
            try {
                set.add(ReservationStatus.valueOf(c));
            } catch (Exception ignored) {}
        }
        return set;
    }

    // ===== 폼 DTO =====
    public static class StoreForm {
        private String city;
        private String name;
        private String lat;
        private String lng;

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getLat() { return lat; }
        public void setLat(String lat) { this.lat = lat; }
        public String getLng() { return lng; }
        public void setLng(String lng) { this.lng = lng; }
    }
}