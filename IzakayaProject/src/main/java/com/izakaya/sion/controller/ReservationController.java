package com.izakaya.sion.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

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

    /* =========================
       예약 폼 화면 (GET)
    ========================= */
    @GetMapping
    public String form(Model model) {
        model.addAttribute("stores", storeRepository.findAll());
        return "service/reservation";
    }

    /* =========================
       예약 요청 처리 (POST)
    ========================= */
    @PostMapping
    public String reserve(
            @RequestParam("storeId") Long storeId,
            @RequestParam("partyCnt") int partyCnt,
            @RequestParam("resvDate") String resvDate,
            @RequestParam("resvTime") String resvTime,
            @RequestParam(value = "reqNote", required = false) String reqNote,
            RedirectAttributes ra
    ) {
        Long custId = 1L; // TODO 로그인 연동 시 세션에서 가져오기

        /* ===== 30분 단위 체크 ===== */
        if (!(resvTime.endsWith(":00") || resvTime.endsWith(":30"))) {
            ra.addFlashAttribute("msg",
                    "時間は30分単位（00 または 30）で選択してください。");
            ra.addFlashAttribute("msgType", "error");
            return "redirect:/reserve";
        }

        LocalDate date;
        LocalTime time;

        try {
            date = LocalDate.parse(resvDate);
            time = LocalTime.parse(resvTime);
        } catch (DateTimeParseException e) {
            ra.addFlashAttribute("msg",
                    "日付または時間の形式が正しくありません。");
            ra.addFlashAttribute("msgType", "error");
            return "redirect:/reserve";
        }

        /* ===== 영업시간 체크 (17:00 ~ 03:00) ===== */
        boolean valid =
                !time.isBefore(LocalTime.of(17, 0)) ||
                !time.isAfter(LocalTime.of(3, 0));

        if (!valid) {
            ra.addFlashAttribute("msg",
                    "予約時間は17:00〜翌03:00の間で選択してください。");
            ra.addFlashAttribute("msgType", "error");
            return "redirect:/reserve";
        }

        /* ===== 자정 이후 예약은 다음날 처리 ===== */
        if (time.isBefore(LocalTime.of(5, 0))) {
            date = date.plusDays(1);
        }

        LocalDateTime resvAt = LocalDateTime.of(date, time);

        reservationService.createPendingReservation(
                storeId,
                custId,
                resvAt,
                partyCnt,
                reqNote
        );

        /* ===== 성공 메시지 ===== */
        ra.addFlashAttribute(
        	    "msg",
        	    "予約リクエストを受け付けました。<br>管理者確認後、LINEで通知されます。"
        	);
        	ra.addFlashAttribute("msgType", "success");

        return "redirect:/reserve";
    }
}