package com.izakaya.sion.service.pos;

import org.springframework.stereotype.Service;

import com.izakaya.sion.entity.*;
import com.izakaya.sion.pos.dto.PosTopbarResponse;
import com.izakaya.sion.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PosTopbarService {

    private final PaymentRepository paymentRepository;

    public PosTopbarResponse todayTopbar(Long storeId) {

        var r = PosBusinessTimeRange.todayBusinessRange();

        long cash = paymentRepository.sumPaidAmountByStoreAndMethodAndPaidAtBetween(
                storeId, PaymentStatus.PAID, PaymentMethod.CASH, r.from(), r.to()
        );

        long card = paymentRepository.sumPaidAmountByStoreAndMethodAndPaidAtBetween(
                storeId, PaymentStatus.PAID, PaymentMethod.CARD, r.from(), r.to()
        );

        return new PosTopbarResponse(cash, card, cash + card);
    }
}