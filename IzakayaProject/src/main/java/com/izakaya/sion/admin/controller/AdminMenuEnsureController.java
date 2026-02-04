package com.izakaya.sion.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.izakaya.sion.admin.dto.MenuEnsureRequest;
import com.izakaya.sion.admin.dto.MenuEnsureResponse;
import com.izakaya.sion.domain.Menu;
import com.izakaya.sion.domain.MenuCategory;
import com.izakaya.sion.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/menus")
public class AdminMenuEnsureController {

    private final MenuRepository menuRepository;

    // ✅ rawCategory(String) -> enum 안전 변환 (ETC가 enum에 없어도 컴파일/런타임 에러 방지)
    private MenuCategory safeCategory(String rawCategory) {
        String rc = (rawCategory == null) ? "" : rawCategory.trim();
        if (rc.isEmpty()) {
            // 아무것도 없으면 첫 번째 enum으로 fallback
            return MenuCategory.values()[0];
        }

        // 1) 대소문자 무시하고 이름 매칭
        for (MenuCategory c : MenuCategory.values()) {
            if (c.name().equalsIgnoreCase(rc)) return c;
        }

        // 2) 흔한 기본값 후보들(프로젝트마다 다를 수 있어서 안전하게)
        for (String fallback : new String[]{"ETC", "OTHER", "DEFAULT"}) {
            for (MenuCategory c : MenuCategory.values()) {
                if (c.name().equalsIgnoreCase(fallback)) return c;
            }
        }

        // 3) 그래도 없으면 첫 번째 enum
        return MenuCategory.values()[0];
    }

    // ✅ "DRINK" 카테고리인지 판단 (enum에 DRINK가 없어도 컴파일 에러 없음)
    private boolean isDrinkCategory(MenuCategory category, String rawCategory) {
        if (category != null && "DRINK".equalsIgnoreCase(category.name())) return true;
        return rawCategory != null && "DRINK".equalsIgnoreCase(rawCategory.trim());
    }

    // ✅ priceText("¥700" 등)에서 숫자만 뽑아 Long으로
    private Long priceTextToLong(String priceText) {
        if (priceText == null) return 0L;
        String digits = priceText.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) return 0L;
        try {
            return Long.parseLong(digits);
        } catch (Exception e) {
            return 0L;
        }
    }

    // ✅ price(Long) -> priceText(String)
    private String longToPriceText(Long price) {
        long p = (price == null) ? 0L : price;
        return "¥" + p;
    }

    /**
     * ✅ 하드코딩 메뉴를 DB에 생성/갱신하고 id를 돌려줌
     * - DRINK 카테고리는 POS 전용 => visible=false 로 저장(사용자 메뉴판에서 숨김)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ensure")
    public List<MenuEnsureResponse> ensure(@RequestBody MenuEnsureRequest req) {

        List<MenuEnsureResponse> out = new ArrayList<>();
        if (req == null || req.getItems() == null) return out;

        for (MenuEnsureRequest.Item it : req.getItems()) {
            if (it == null) continue;

            String rawCategory = (it.getCategory() == null) ? "" : it.getCategory().trim();
            String name = (it.getName() == null) ? "" : it.getName().trim();
            Long price = (it.getPrice() == null) ? 0L : it.getPrice();

            if (name.isEmpty()) continue;

            MenuCategory category = safeCategory(rawCategory);
            boolean isDrink = isDrinkCategory(category, rawCategory);

            Menu menu = menuRepository.findByCategoryAndName(category, name)
                    .orElseGet(() -> {
                        Menu m = new Menu();
                        m.setCategory(category);
                        m.setName(name);

                        // ✅ 엔티티에 price(Long) 필드가 없으므로 priceText만 세팅
                        m.setPriceText(longToPriceText(price));

                        // ✅ DRINK는 사용자 메뉴판에서 숨김
                        m.setVisible(!isDrink);

                        // ✅ 기본 정렬
                        m.setSortOrder(9999);

                        return menuRepository.save(m);
                    });

            // ✅ 가격(priceText) 변경 시 갱신
            String newPriceText = longToPriceText(price);
            if (menu.getPriceText() == null || !menu.getPriceText().equals(newPriceText)) {
                menu.setPriceText(newPriceText);
            }

            // ✅ DRINK 여부에 따라 visible 보정(기존 데이터가 잘못 저장된 경우도 정리)
            Boolean wantVisible = !isDrink;
            if (menu.getVisible() == null || !menu.getVisible().equals(wantVisible)) {
                menu.setVisible(wantVisible);
            }

            // ✅ 변경사항이 있으면 저장
            menuRepository.save(menu);

            // ✅ 응답 price(Long)는 priceText에서 숫자 추출해서 내려줌
            out.add(new MenuEnsureResponse(
                    menu.getId(),
                    menu.getCategory().name(),
                    menu.getName(),
                    priceTextToLong(menu.getPriceText())
            ));
        }

        return out;
    }

    /**
     * ✅ POS에서만 전체 메뉴(visible=false 포함) 불러오기용
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<MenuEnsureResponse> all() {
        List<MenuEnsureResponse> out = new ArrayList<>();
        List<Menu> list = menuRepository.findAllByOrderBySortOrderAscIdDesc();

        for (Menu m : list) {
            out.add(new MenuEnsureResponse(
                    m.getId(),
                    m.getCategory().name(),
                    m.getName(),
                    priceTextToLong(m.getPriceText())
            ));
        }
        return out;
    }
}