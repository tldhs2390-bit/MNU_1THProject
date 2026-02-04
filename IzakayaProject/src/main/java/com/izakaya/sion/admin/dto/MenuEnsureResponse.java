package com.izakaya.sion.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuEnsureResponse {
    private Long id;
    private String category;
    private String name;
    private Long price;
}