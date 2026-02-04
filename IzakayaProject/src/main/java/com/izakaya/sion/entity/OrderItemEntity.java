package com.izakaya.sion.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pos_order_item",
       indexes = {
           @Index(name = "idx_pos_item_order", columnList = "order_id")
       })
@Getter @Setter
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ 어떤 주문에 속하는지
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    // ✅ 메뉴 정보(지금 메뉴 엔티티가 있다면 ManyToOne으로 연결 가능)
    //    일단 POS 스냅샷으로 name/price 저장 (메뉴가 삭제/변경돼도 주문 기록은 유지)
    @Column(name = "menu_name", nullable = false, length = 120)
    private String menuName;

    @Column(name = "unit_price", nullable = false)
    private Long unitPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "line_total", nullable = false)
    private Long lineTotal;

    public void recalcLineTotal() {
        long price = (unitPrice == null) ? 0L : unitPrice;
        int qty = (quantity == null) ? 0 : quantity;
        this.lineTotal = price * qty;
    }
}