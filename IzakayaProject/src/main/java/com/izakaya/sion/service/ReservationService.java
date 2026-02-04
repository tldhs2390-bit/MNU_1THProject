package com.izakaya.sion.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izakaya.sion.entity.ReservationEntity;
import com.izakaya.sion.entity.ReservationStatus;
import com.izakaya.sion.repository.ReservationRepository;
import com.izakaya.sion.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void createPendingReservation(
            Long storeId,
            Long custId,
            String customerName,
            String phone,              // ✅✅✅ 추가
            LocalDateTime resvAt,
            int partyCnt,
            String reqNote,
            String lineUserId
    ) {
        var store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("store not found: " + storeId));

        ReservationEntity r = new ReservationEntity();
        r.setStore(store);
        r.setCustomerId(custId);

        r.setCustomerName(customerName);
        r.setPhone(phone);           // ✅✅✅ 추가

        r.setVisitAt(resvAt);
        r.setHeadcount(partyCnt);
        r.setReqNote(reqNote);

        if (lineUserId != null && !lineUserId.isBlank()) r.setLineUserId(lineUserId);
        else r.setLineUserId(null);

        r.setStatus(ReservationStatus.PENDING);

        reservationRepository.save(r);
    }
}