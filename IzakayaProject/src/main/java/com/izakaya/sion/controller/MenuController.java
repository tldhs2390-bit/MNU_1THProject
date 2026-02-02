package com.izakaya.sion.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.izakaya.sion.domain.Menu;
import com.izakaya.sion.repository.MenuRepository;
import com.izakaya.sion.service.MenuService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuRepository menuRepository;
    private final MenuService menuService;

    // ✅ 간단 목록(공개 메뉴만)
    @GetMapping("/menu")
    public String menuList(Model model) {
        List<Menu> menus = menuRepository.findAllByVisibleTrueOrderBySortOrderAscIdDesc();
        model.addAttribute("menus", menus);
        return "menu_list";
    }

    // ✅ 상세 페이지
    @GetMapping("/menu/{id}")
    public String menuDetail(@PathVariable("id") Long id, Model model) {
        Menu menu = menuService.findById(id);
        model.addAttribute("menu", menu);
        return "menu_detail";
    }
}