package com.izakaya.sion.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MenuEnsureRequest {
    private Long storeId; // 지금은 사용만 받아둠(향후 점포별 메뉴면 확장)
    private List<Item> items;

    @Getter @Setter
    public static class Item {
        private String category; // "DRINK" 등
        private String name;
        private Long price;
    }
}