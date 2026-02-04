package com.izakaya.sion.pos.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.izakaya.sion.pos.dto.PosTodaySummaryResponse;
import com.izakaya.sion.service.pos.PosSummaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pos")
public class PosApiController {

    private final PosSummaryService posSummaryService;

    @GetMapping("/{storeId}/today-summary")
    @PreAuthorize("isAuthenticated()")
    public PosTodaySummaryResponse todaySummary(@PathVariable("storeId") Long storeId) {
        return posSummaryService.todaySummary(storeId);
    }
}