package com.izakaya.sion.admin.controller;

import com.izakaya.sion.entity.StoreEntity;
import com.izakaya.sion.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/store")
public class AdminStoreController {

    private final StoreRepository storeRepository;

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
    // 🔥 지금 뜬 500 에러(-parameters) 방지: @PathVariable("id") 처럼 이름을 명시
    @PostMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable("id") Long id) {
        storeRepository.deleteById(id);
        return "redirect:/admin/store?deleted";
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