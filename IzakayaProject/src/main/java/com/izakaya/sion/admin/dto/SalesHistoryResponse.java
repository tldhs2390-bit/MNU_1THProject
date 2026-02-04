package com.izakaya.sion.admin.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SalesHistoryResponse {

    private Summary summary = new Summary();
    private List<Item> items = new ArrayList<>();

    @Data
    public static class Summary {
        private long cash;
        private long card;
        private long total;
    }

    @Data
    public static class Item {
        private Long saleId;          // PaymentEntity id
        private String paidAt;        // yyyy-MM-ddTHH:mm:ss (LocalDateTime toString)
        private Integer tableNo;      // OrderEntity tableNo (없으면 null)
        private String method;        // CASH/CARD/MIXED...
        private long cashAmount;
        private long cardAmount;
        private long totalAmount;
        private List<MenuLine> menus = new ArrayList<>();
    }

    @Data
    public static class MenuLine {
        private String name;
        private int qty;
        private long price; // 단가(또는 라인 단가 기준이면 JS에서 qty*price가 어긋나니 "단가"로 맞추세요)
    }
}