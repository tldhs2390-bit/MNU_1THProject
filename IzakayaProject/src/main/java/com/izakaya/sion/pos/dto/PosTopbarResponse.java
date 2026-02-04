package com.izakaya.sion.pos.dto;

public record PosTopbarResponse(
        long cashSales,
        long cardSales,
        long totalSales
) {}