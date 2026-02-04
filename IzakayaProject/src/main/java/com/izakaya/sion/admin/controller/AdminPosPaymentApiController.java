package com.izakaya.sion.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.izakaya.sion.pos.dto.PosPayRequest;
import com.izakaya.sion.pos.dto.PosPayResponse;
import com.izakaya.sion.service.pos.PosPaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/store")
public class AdminPosPaymentApiController {

    private final PosPaymentService posPaymentService;

    @PostMapping("/{storeId}/pos/pay")
    @PreAuthorize("isAuthenticated()")
    public PosPayResponse pay(@PathVariable("storeId") Long storeId,
                              @RequestBody PosPayRequest req) {
        return posPaymentService.pay(storeId, req);
    }
}