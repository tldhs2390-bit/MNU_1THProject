package com.izakaya.sion.admin.controller;

import com.izakaya.sion.entity.StoreEntity;
import com.izakaya.sion.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/store")
public class AdminStorePosPageController {

    private final StoreRepository storeRepository; // ✅ 추가

    /**
     * ✅ 테이블 선택 POS 화면
     * URL: /admin/store/{storeId}/table-pos
     */
    @GetMapping("/{storeId}/table-pos")
    public String tablePos(@PathVariable("storeId") Long storeId, Model model) {

        StoreEntity store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("store not found: " + storeId));

        model.addAttribute("storeId", store.getId());
        model.addAttribute("storeName", store.getName()); // ✅ 이게 핵심

        return "admin/table_pos"; 
        // ⚠️ 실제 위치: templates/admin/table_pos.html 기준
    }

    /**
     * ✅ 기존 POS 화면 (유지)
     */
    @GetMapping("/{storeId}/pos")
    public String storePos(@PathVariable("storeId") Long storeId,
                           @RequestParam(required = false) Integer tableNo,
                           Model model) {

        model.addAttribute("storeId", storeId);
        model.addAttribute("tableNo", tableNo);

        return "admin/store_pos";
    }
}