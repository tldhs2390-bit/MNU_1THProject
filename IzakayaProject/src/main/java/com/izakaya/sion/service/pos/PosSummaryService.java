package com.izakaya.sion.service.pos;

import java.util.List;

import org.springframework.stereotype.Service;

import com.izakaya.sion.entity.PaymentStatus;
import com.izakaya.sion.pos.dto.PosTodaySummaryResponse;
import com.izakaya.sion.repository.OrderItemRepository;
import com.izakaya.sion.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PosSummaryService {

    private final PaymentRepository paymentRepository;
    private final OrderItemRepository orderItemRepository;

    public PosTodaySummaryResponse todaySummary(Long storeId) {

        var r = PosBusinessTimeRange.todayBusinessRange();

        long sales = paymentRepository.sumPaidAmountByStoreAndPaidAtBetween(
                storeId, PaymentStatus.PAID, r.from(), r.to()
        );

        long paidOrders = paymentRepository.countPaidByStoreAndPaidAtBetween(
                storeId, PaymentStatus.PAID, r.from(), r.to()
        );

        List<Object[]> rows = orderItemRepository.topMenusByQty(
                storeId, r.from(), r.to()
        );

        var top = rows.stream()
                .limit(10)
                .map(x -> new PosTodaySummaryResponse.TopMenuItem(
                        (String) x[0],
                        ((Number) x[1]).longValue(),
                        ((Number) x[2]).longValue()
                ))
                .toList();

        return new PosTodaySummaryResponse(sales, paidOrders, top);
    }
}