package com.izakaya.sion.admin.dto;

public record AdminStoreTodayResponse(
        Long storeId,
        long todaySales,
        long reservationCount
) {}