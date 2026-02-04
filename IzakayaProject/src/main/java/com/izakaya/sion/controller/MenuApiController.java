package com.izakaya.sion.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.izakaya.sion.domain.Menu;
import com.izakaya.sion.domain.MenuCategory;
import com.izakaya.sion.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MenuApiController {

    private final MenuRepository menuRepository;

    @GetMapping("/api/menus")
    @PreAuthorize("isAuthenticated()")
    public List<MenuItem> menus() {
        return menuRepository.findAll().stream()
                .map(m -> new MenuItem(
                        m.getId(),
                        m.getCategory(),                 // ✅ 카테고리 포함
                        m.getName(),
                        parsePriceYen(m.getPriceText())  // ✅ priceText -> int 변환
                ))
                .toList();
    }

    // ✅ "¥980（税込）", "※1人前 ¥1,680（税込）" 같은 문자열에서 숫자만 추출
    private Integer parsePriceYen(String priceText) {
        if (priceText == null) return 0;

        // 콤마 제거 후 숫자만 남김
        String digits = priceText.replace(",", "").replaceAll("[^0-9]", "");
        if (digits.isBlank()) return 0;

        try {
            return Integer.parseInt(digits);
        } catch (Exception e) {
            return 0;
        }
    }

    public record MenuItem(Long id, MenuCategory category, String name, Integer price) {}
}