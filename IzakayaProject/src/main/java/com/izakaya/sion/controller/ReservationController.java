package com.izakaya.sion.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.izakaya.sion.repository.StoreRepository;
import com.izakaya.sion.service.ReservationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReservationController {

    private final ReservationService reservationService;
    private final StoreRepository storeRepository;

    @GetMapping
    public String form(Model model, HttpSession session) {
        model.addAttribute("stores", storeRepository.findAll());
        model.addAttribute("lineLinked", session.getAttribute("LINE_USER_ID") != null);
        return "service/reservation";
    }

    @PostMapping
    public String reserve(
            @RequestParam("storeId") Long storeId,
            @RequestParam("customerName") String customerName,
            @RequestParam("phone") String phone, // ✅✅✅ 추가
            @RequestParam("partyCnt") int partyCnt,
            @RequestParam("resvDate") String resvDate,
            @RequestParam("resvTime") String resvTime,
            @RequestParam(value = "reqNote", required = false) String reqNote,
            RedirectAttributes ra,
            HttpSession session
    ) {
        Long custId = 1L; // TODO 로그인 연동 시 세션에서 가져오기

        if (customerName == null || customerName.trim().isEmpty()) {
            ra.addFlashAttribute("msg", "お名前を入力してください。");
            ra.addFlashAttribute("msgType", "error");
            return "redirect:/reserve";
        }

        // ✅ phone not null 대응 (형식은 빡세게 안 잡고, 최소만)
        if (phone == null || phone.trim().isEmpty()) {
            ra.addFlashAttribute("msg", "電話番号を入力してください。");
            ra.addFlashAttribute("msgType", "error");
            return "redirect:/reserve";
        }

        if (!(resvTime.endsWith(":00") || resvTime.endsWith(":30"))) {
            ra.addFlashAttribute("msg", "時間は30分単位（00 または 30）で選択してください。");
            ra.addFlashAttribute("msgType", "error");
            return "redirect:/reserve";
        }

        LocalDate date;
        LocalTime time;

        try {
            date = LocalDate.parse(resvDate);
            time = LocalTime.parse(resvTime);
        } catch (DateTimeParseException e) {
            ra.addFlashAttribute("msg", "日付または時間の形式が正しくありません。");
            ra.addFlashAttribute("msgType", "error");
            return "redirect:/reserve";
        }

        boolean inBusinessHours =
                (!time.isBefore(LocalTime.of(17, 0))) || (!time.isAfter(LocalTime.of(3, 0)));

        if (!inBusinessHours) {
            ra.addFlashAttribute("msg", "予約時間は17:00〜翌03:00の間で選択してください。");
            ra.addFlashAttribute("msgType", "error");
            return "redirect:/reserve";
        }

        if (time.isBefore(LocalTime.of(5, 0))) {
            date = date.plusDays(1);
        }

        LocalDateTime resvAt = LocalDateTime.of(date, time);

        String lineUserId = (String) session.getAttribute("LINE_USER_ID");

        reservationService.createPendingReservation(
                storeId,
                custId,
                customerName.trim(),
                phone.trim(),         // ✅✅✅ 추가
                resvAt,
                partyCnt,
                reqNote,
                lineUserId
        );

        boolean linked = (lineUserId != null && !lineUserId.isBlank());
        ra.addFlashAttribute(
                "msg",
                linked
                    ? "予約リクエストを受け付けました。<br>管理者確認後、LINEで通知されます。"
                    : "予約リクエストを受け付けました。<br>※LINE連携すると結果をLINEで通知できます。"
        );
        ra.addFlashAttribute("msgType", "success");

        return "redirect:/reserve";
    }
}