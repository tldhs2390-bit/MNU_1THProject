package com.izakaya.sion.admin.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.izakaya.sion.domain.Menu;
import com.izakaya.sion.domain.MenuCategory;
import com.izakaya.sion.service.MenuService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/menu")
public class AdminMenuController {

    private final MenuService menuService;

    /* =========================
       등록 폼
    ========================= */
    @GetMapping("/insert")
    public String insertForm(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("categories", List.of(
                MenuCategory.SINGLE,
                MenuCategory.MAIN,
                MenuCategory.FRIED_PAN,
                MenuCategory.SNACK
        ));
        return "admin/menu_insert";
    }

    /* =========================
       등록 처리
       - ✅ imageFile이 실제로 들어오는지 로그로 확정
       - ✅ 업로드 실패(예외)면 저장 안 하고 바로 터뜨려서 DB null 방지
    ========================= */
    @PostMapping("/insert")
    public String insert(@ModelAttribute("menu") Menu menu,
                         @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        // ✅ 기본값
        if (menu.getVisible() == null) menu.setVisible(true);
        if (menu.getSortOrder() == null) menu.setSortOrder(9999);

        // ✅ 업로드 바인딩 확인 로그 (서버 콘솔에서 확인)
        System.out.println("[ADMIN MENU INSERT] imageFile = " + (imageFile == null ? "null" : imageFile.getOriginalFilename()));
        System.out.println("[ADMIN MENU INSERT] isEmpty   = " + (imageFile == null ? "null" : imageFile.isEmpty()));
        System.out.println("[ADMIN MENU INSERT] size      = " + (imageFile == null ? "null" : imageFile.getSize()));

        // ✅ 이미지 저장
        if (imageFile != null && !imageFile.isEmpty()) {
            // (선택) 너무 큰 파일 방어 (원하시면 숫자 조절)
            if (imageFile.getSize() > 5L * 1024 * 1024) {
                throw new IllegalArgumentException("画像が大きすぎます（5MB以上）");
            }

            String savedPath = menuService.saveMenuImage(imageFile);
            System.out.println("[ADMIN MENU INSERT] savedPath = " + savedPath);

            menu.setImageUrl(savedPath);
        } else {
            System.out.println("[ADMIN MENU INSERT] NO IMAGE UPLOADED");
        }

        // ✅ 저장 직전 값 확인
        System.out.println("[ADMIN MENU INSERT] menu.imageUrl(before save) = " + menu.getImageUrl());

        menuService.save(menu);

        return "redirect:/admin/menu/list";
    }

    /* =========================
       관리자 목록 (4개 그룹)
    ========================= */
    @GetMapping("/list")
    public String list(Model model) {

        List<Menu> items = menuService.findAllForAdmin();
        model.addAttribute("items", items);

        List<MenuCategory> order = List.of(
                MenuCategory.SINGLE,     // 단품메뉴
                MenuCategory.MAIN,       // 면·밥류
                MenuCategory.FRIED_PAN,  // 튀김·전류
                MenuCategory.SNACK       // 간단안주류
        );

        Map<MenuCategory, List<Menu>> groupedItems = new LinkedHashMap<>();
        for (MenuCategory c : order) groupedItems.put(c, new ArrayList<>());

        for (Menu m : items) {
            MenuCategory c =
                    (m.getCategory() != null && groupedItems.containsKey(m.getCategory()))
                            ? m.getCategory()
                            : MenuCategory.SINGLE;
            groupedItems.get(c).add(m);
        }

        model.addAttribute("groupedItems", groupedItems);
        return "admin/menu_list";
    }

    /* =========================
       수정 폼
    ========================= */
    @GetMapping("/edit")
    public String editForm(@RequestParam("id") Long id, Model model) {

        Menu menu = menuService.findById(id);

        model.addAttribute("menu", menu);
        model.addAttribute("categories", List.of(
                MenuCategory.SINGLE,
                MenuCategory.MAIN,
                MenuCategory.FRIED_PAN,
                MenuCategory.SNACK
        ));

        return "admin/menu_modify";
    }

    /* =========================
       수정 처리
       - ✅ imageFile 바인딩 로그
       - ✅ removeImage 우선
       - ✅ 새 파일 없으면 기존 이미지 유지
    ========================= */
    @PostMapping("/edit")
    public String edit(@ModelAttribute("menu") Menu form,
                       @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                       @RequestParam(value = "removeImage", required = false) Boolean removeImage) {

        Menu origin = menuService.findById(form.getId());

        origin.setCategory(form.getCategory());
        origin.setSortOrder(form.getSortOrder() != null ? form.getSortOrder() : origin.getSortOrder());
        origin.setName(form.getName());
        origin.setDescription(form.getDescription());
        origin.setPriceText(form.getPriceText());
        origin.setTagText(form.getTagText());
        origin.setOriginText(form.getOriginText());
        origin.setAllergyText(form.getAllergyText());
        origin.setVisible(form.getVisible() != null ? form.getVisible() : origin.getVisible());

        try {
            String ht = (String) Menu.class.getMethod("getHighlightText").invoke(form);
            Menu.class.getMethod("setHighlightText", String.class).invoke(origin, ht);
        } catch (Exception ignore) {}

        System.out.println("[ADMIN MENU EDIT] imageFile = " + (imageFile == null ? "null" : imageFile.getOriginalFilename()));
        System.out.println("[ADMIN MENU EDIT] isEmpty   = " + (imageFile == null ? "null" : imageFile.isEmpty()));
        System.out.println("[ADMIN MENU EDIT] size      = " + (imageFile == null ? "null" : imageFile.getSize()));

        if (Boolean.TRUE.equals(removeImage)) {
            origin.setImageUrl(null);
        } else if (imageFile != null && !imageFile.isEmpty()) {
            if (imageFile.getSize() > 5L * 1024 * 1024) {
                throw new IllegalArgumentException("画像が大きすぎます（5MB以上）");
            }
            origin.setImageUrl(menuService.saveMenuImage(imageFile));
        } // ✅ else: 기존 이미지 유지

        System.out.println("[ADMIN MENU EDIT] origin.imageUrl(before save) = " + origin.getImageUrl());

        menuService.save(origin);
        return "redirect:/admin/menu/list";
    }

    /* =========================
       삭제
    ========================= */
    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        menuService.delete(id);
        return "redirect:/admin/menu/list";
    }
}