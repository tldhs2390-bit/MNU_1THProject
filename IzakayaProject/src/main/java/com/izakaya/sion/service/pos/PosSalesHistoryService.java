package com.izakaya.sion.service.pos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izakaya.sion.admin.dto.SalesHistoryResponse;
import com.izakaya.sion.entity.OrderEntity;
import com.izakaya.sion.entity.OrderItemEntity;
import com.izakaya.sion.entity.PaymentEntity;
import com.izakaya.sion.entity.PaymentMethod;
import com.izakaya.sion.entity.PaymentStatus;
import com.izakaya.sion.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PosSalesHistoryService {

    private final PaymentRepository paymentRepository;

    public SalesHistoryResponse getSalesHistory(Long storeId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        // ✅ PAID만(실매출)
        List<PaymentEntity> pays =
                paymentRepository.findSalesByStoreAndDateWithStatus(storeId, PaymentStatus.PAID, start, end);

        SalesHistoryResponse res = new SalesHistoryResponse();
        long sumCash = 0L;
        long sumCard = 0L;
        long sumTotal = 0L;

        for (PaymentEntity p : pays) {
            if (p.getPaidAt() == null) continue;

            SalesHistoryResponse.Item item = new SalesHistoryResponse.Item();
            item.setSaleId(p.getId());
            item.setPaidAt(p.getPaidAt().toString());

            PaymentMethod method = p.getMethod();
            item.setMethod(method == null ? null : method.name());

            long total = safeLong(p.getPaidAmount());

            long cash = 0L;
            long card = 0L;

            if (method == PaymentMethod.CASH) cash = total;
            else if (method == PaymentMethod.CARD) card = total;

            item.setTotalAmount(total);
            item.setCashAmount(cash);
            item.setCardAmount(card);

            sumCash += cash;
            sumCard += card;
            sumTotal += total;

            // ✅ 주문/품목 + 테이블번호
            OrderEntity order = p.getOrder();
            if (order != null) {
                item.setTableNo(order.getTableNo());      // ✅ OrderEntity에 tableNo 추가했으니 이제 오류 없음
                item.setMenus(buildMenuLines(order));     // ✅ OrderItemEntity 스냅샷 기반
            }

            res.getItems().add(item);
        }

        res.getSummary().setCash(sumCash);
        res.getSummary().setCard(sumCard);
        res.getSummary().setTotal(sumTotal);

        return res;
    }

    private List<SalesHistoryResponse.MenuLine> buildMenuLines(OrderEntity order) {
        List<SalesHistoryResponse.MenuLine> out = new ArrayList<>();

        List<OrderItemEntity> items = order.getItems();
        if (items == null) return out;

        for (OrderItemEntity oi : items) {
            SalesHistoryResponse.MenuLine ml = new SalesHistoryResponse.MenuLine();

            // ✅✅✅ OrderItemEntity 구조에 맞게: menuName / unitPrice / quantity
            ml.setName(oi.getMenuName());
            ml.setPrice(safeLong(oi.getUnitPrice()));        // 단가
            ml.setQty(safeInt(oi.getQuantity()));

            out.add(ml);
        }

        return out;
    }

    private long safeLong(Long v) { return v == null ? 0L : v; }
    private int safeInt(Integer v) { return v == null ? 0 : v; }
}