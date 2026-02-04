package com.izakaya.sion.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.izakaya.sion.admin.dto.AdminStoreTodayResponse;
import com.izakaya.sion.service.AdminStoreRealtimeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/store")
public class AdminStoreRealtimeController {

	private final AdminStoreRealtimeService realtimeService;

    @GetMapping("/admin/store/today-sales")
    @PreAuthorize("isAuthenticated()")
    public AdminStoreTodayResponse todaySales(@RequestParam("storeId") Long storeId) {
        return realtimeService.todaySummary(storeId);
    }
}