package com.izakaya.sion.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izakaya.sion.entity.ReservationEntity;
import com.izakaya.sion.repository.CustRepository;
import com.izakaya.sion.repository.ReservationRepository;
import com.izakaya.sion.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustRepository custRepository;
    private final StoreRepository storeRepository;
    private final LineMessageService lineMessageService;

    /**
     * 예약 요청 생성 (PENDING)
     */
    @Transactional
    public Long createPendingReservation(
            Long storeId,
            Long custId,
            LocalDateTime resvAt,
            int partyCnt,
            String reqNote
    ) {
        ReservationEntity saved = reservationRepository.save(
                ReservationEntity.createPending(storeId, custId, resvAt, partyCnt, reqNote)
        );
        return saved.getResvId();
    }

    /**
     * 관리자 확인 → 예약 확정 + LINE 발송
     */
    @Transactional
    public void confirmReservation(Long resvId) {

        ReservationEntity resv = reservationRepository.findById(resvId)
                .orElseThrow(() -> new IllegalArgumentException("予約が存在しません。"));

        resv.confirm(); // 상태 변경

        String lineUid = custRepository.findById(resv.getCustId())
                .map(c -> c.getLineUid())
                .orElse(null);

        if (lineUid == null || lineUid.isBlank()) return;

        String storeTitle = storeRepository.findById(resv.getStoreId())
                .map(s -> s.getCity() + " " + s.getName())
                .orElse("店舗情報不明");

        String text =
                "【予約確定】\n" +
                "店舗: " + storeTitle + "\n" +
                "人数: " + resv.getPartyCnt() + "名\n" +
                "予約日時: " + resv.getResvAt() + "\n\n" +
                "予約が確定しました。\n" +
                "ご来店お待ちしております。";

        lineMessageService.pushText(lineUid, text);
    }
}