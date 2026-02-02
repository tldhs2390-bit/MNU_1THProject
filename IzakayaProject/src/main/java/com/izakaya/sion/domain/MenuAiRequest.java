package com.izakaya.sion.domain;

import java.util.List;

import lombok.Data;

@Data
public class MenuAiRequest {
    private String name;
    private String price;
    private String allergy;
    private String origin;
    private List<String> allowedCategories;

    // ✅ 추가: base64 이미지(선택)
    // 예) "data:image/png;base64,AAAA..."
    private String imageBase64;
    private String features;
   }