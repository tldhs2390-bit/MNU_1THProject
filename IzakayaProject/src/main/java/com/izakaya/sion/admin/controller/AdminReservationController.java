package com.izakaya.sion.admin.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.izakaya.sion.admin.dto.PendingReservationView;
import com.izakaya.sion.entity.ReservationEntity;
import com.izakaya.sion.entity.ReservationStatus;
import com.izakaya.sion.external.LineMessagingService;
import com.izakaya.sion.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/store/{storeId}/reservations")
public class AdminReservationController {

    private final ReservationRepository reservationRepository;
    private final LineMessagingService lineMessagingService;

    // ===== 승인 대기 =====
    @GetMapping("/pending")
    @PreAuthorize("isAuthenticated()")
    public List<PendingReservationView> pending(@PathVariable("storeId") Long storeId) {
        return reservationRepository
                .findTop20ByStore_IdAndStatusOrderByCreatedAtDesc(storeId, ReservationStatus.PENDING);
    }

    // ===== 승인 완료 =====
    @GetMapping("/accepted")
    @PreAuthorize("isAuthenticated()")
    public List<ReservationEntity> accepted(@PathVariable("storeId") Long storeId) {
        return reservationRepository
                .findByStore_IdAndStatusOrderByVisitAtAsc(storeId, ReservationStatus.ACCEPTED);
    }

    // ===== ✅ 예약 이력(날짜별 / 상태 전체) =====
    @GetMapping("/history")
    @PreAuthorize("isAuthenticated()")
    public List<ReservationEntity> history(
            @PathVariable("storeId") Long storeId,
            @RequestParam("date") String date
    ) {
        LocalDate ymd = LocalDate.parse(date);
        LocalDateTime start = ymd.atStartOfDay();
        LocalDateTime end = ymd.plusDays(1).atStartOfDay();

        return reservationRepository.findByStoreIdAndVisitAtBetweenOrderByVisitAtAsc(storeId, start, end);
    }

    // ===== 수락 =====
    @PostMapping("/{reservationId}/accept")
    @PreAuthorize("isAuthenticated()")
    public void accept(
            @PathVariable("storeId") Long storeId,
            @PathVariable("reservationId") Long reservationId
    ) {
        ReservationEntity r = reservationRepository.findById(reservationId).orElseThrow();

        if (!r.getStore().getId().equals(storeId)) {
            throw new IllegalArgumentException("store mismatch");
        }

        r.setStatus(ReservationStatus.ACCEPTED);
        reservationRepository.save(r);

        if (r.getLineUserId() != null && !r.getLineUserId().isBlank()) {
            lineMessagingService.pushText(
                    r.getLineUserId(),
                    "予約が確定しました。ご来店をお待ちしております。"
            );
        }
    }

    // ===== 거절 =====
    @PostMapping("/{reservationId}/reject")
    @PreAuthorize("isAuthenticated()")
    public void reject(
            @PathVariable("storeId") Long storeId,
            @PathVariable("reservationId") Long reservationId
    ) {
        ReservationEntity r = reservationRepository.findById(reservationId).orElseThrow();

        if (!r.getStore().getId().equals(storeId)) {
            throw new IllegalArgumentException("store mismatch");
        }

        r.setStatus(ReservationStatus.REJECTED);
        reservationRepository.save(r);

        if (r.getLineUserId() != null && !r.getLineUserId().isBlank()) {
            lineMessagingService.pushText(
                    r.getLineUserId(),
                    "申し訳ございません。今回は予約をお受けできませんでした。"
            );
        }
    }
}