package com.izakaya.sion.pos.dto;

import java.util.List;

public class PosPayRequest {

    private String method; // CASH / CARD
    private Integer tableNo;

    private List<Item> items;

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    // ✅✅✅ 추가 (PosPaymentService에서 req.getTableNo() 사용)
    public Integer getTableNo() { return tableNo; }
    public void setTableNo(Integer tableNo) { this.tableNo = tableNo; }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public static class Item {
        private Long menuId;
        private String menuCode;  // ✅ 추가

        private Integer qty;

        public Long getMenuId() { return menuId; }
        public void setMenuId(Long menuId) { this.menuId = menuId; }
        public String getMenuCode() { return menuCode; }

        public Integer getQty() { return qty; }
        public void setQty(Integer qty) { this.qty = qty; }
    }
}