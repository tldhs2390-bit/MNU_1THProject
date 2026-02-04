package com.izakaya.sion.pos.dto;

import java.util.List;

public class PosTodaySummaryResponse {

    private long todaySales;
    private long todayPaidOrders;
    private List<TopMenuItem> topMenus;

    public PosTodaySummaryResponse() {}

    public PosTodaySummaryResponse(long todaySales, long todayPaidOrders, List<TopMenuItem> topMenus) {
        this.todaySales = todaySales;
        this.todayPaidOrders = todayPaidOrders;
        this.topMenus = topMenus;
    }

    public long getTodaySales() { return todaySales; }
    public void setTodaySales(long todaySales) { this.todaySales = todaySales; }

    public long getTodayPaidOrders() { return todayPaidOrders; }
    public void setTodayPaidOrders(long todayPaidOrders) { this.todayPaidOrders = todayPaidOrders; }

    public List<TopMenuItem> getTopMenus() { return topMenus; }
    public void setTopMenus(List<TopMenuItem> topMenus) { this.topMenus = topMenus; }

    public static class TopMenuItem {
        private String menuName;
        private long qty;
        private long amount;

        public TopMenuItem() {}
        public TopMenuItem(String menuName, long qty, long amount) {
            this.menuName = menuName;
            this.qty = qty;
            this.amount = amount;
        }

        public String getMenuName() { return menuName; }
        public void setMenuName(String menuName) { this.menuName = menuName; }

        public long getQty() { return qty; }
        public void setQty(long qty) { this.qty = qty; }

        public long getAmount() { return amount; }
        public void setAmount(long amount) { this.amount = amount; }
    }
}