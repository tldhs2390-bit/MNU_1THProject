package com.izakaya.sion.admin.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.izakaya.sion.admin.dto.SalesHistoryResponse;
import com.izakaya.sion.service.pos.PosSalesHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminSalesHistoryApiController {

    private final PosSalesHistoryService posSalesHistoryService;

    // ✅ JS가 호출하는 URL과 100% 동일
    @GetMapping("/admin/store/{storeId}/sales/history")
    public SalesHistoryResponse salesHistory(
            @PathVariable("storeId") Long storeId,
            @RequestParam("date") String date
    ) {
        LocalDate ymd = LocalDate.parse(date); // YYYY-MM-DD
        return posSalesHistoryService.getSalesHistory(storeId, ymd);
    }
}