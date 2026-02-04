package com.izakaya.sion.service;

import com.izakaya.sion.entity.*;
import com.izakaya.sion.repository.OrderRepository;
import com.izakaya.sion.repository.PaymentRepository;
import com.izakaya.sion.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PosService {

    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public OrderEntity createOrder(Long storeId, List<OrderItemEntity> items, String memo) {
        StoreEntity store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("store not found: " + storeId));

        OrderEntity order = new OrderEntity();
        order.setStore(store);
        order.setStatus(OrderStatus.CREATED);
        order.setMemo(memo);

        long total = 0L;

        for (OrderItemEntity item : items) {
            item.setOrder(order);
            item.recalcLineTotal();
            total += (item.getLineTotal() == null ? 0L : item.getLineTotal());
            order.getItems().add(item);
        }

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    @Transactional
    public PaymentEntity payOrder(Long orderId, String method) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("order not found: " + orderId));

        if (order.getStatus() == OrderStatus.CANCELED) {
            throw new IllegalStateException("canceled order cannot be paid");
        }

        // 이미 결제가 있으면 그대로 반환
        PaymentEntity existing = order.getPayment();
        if (existing != null && existing.getStatus() == PaymentStatus.PAID) {
            return existing;
        }

        PaymentEntity payment = (existing != null) ? existing : new PaymentEntity();
        payment.setOrder(order);

        // ✅ 여기 핵심: String → PaymentMethod 변환
        PaymentMethod payMethod = PaymentMethod.CASH;
        if (method != null && !method.isBlank()) {
            try {
                payMethod = PaymentMethod.valueOf(method.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                payMethod = PaymentMethod.CASH; // 안전 폴백
            }
        }
        payment.setMethod(payMethod);

        payment.setPaidAmount(order.getTotalAmount() == null ? 0L : order.getTotalAmount());
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidAt(LocalDateTime.now());

        PaymentEntity saved = paymentRepository.save(payment);

        order.setPayment(saved);
        order.setStatus(OrderStatus.PAID);

        return saved;
    }
}