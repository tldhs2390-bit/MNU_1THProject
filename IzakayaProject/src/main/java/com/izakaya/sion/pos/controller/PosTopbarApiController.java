package com.izakaya.sion.pos.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.izakaya.sion.pos.dto.PosTopbarResponse;
import com.izakaya.sion.service.pos.PosTopbarService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pos")
public class PosTopbarApiController {

    private final PosTopbarService posTopbarService;

    @GetMapping("/{storeId}/topbar")
    @PreAuthorize("isAuthenticated()")
    public PosTopbarResponse topbar(@PathVariable("storeId") Long storeId) {
        return posTopbarService.todayTopbar(storeId);
    }
}