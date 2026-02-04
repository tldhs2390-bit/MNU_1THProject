package com.izakaya.sion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izakaya.sion.domain.Menu;
import com.izakaya.sion.domain.MenuCategory;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByOrderBySortOrderAscIdDesc();

    List<Menu> findAllByVisibleTrueOrderBySortOrderAscIdDesc();

    List<Menu> findAllByVisibleTrueAndCategoryOrderBySortOrderAscIdDesc(MenuCategory category);

    // ✅✅✅ enum 타입으로 맞춤
    Optional<Menu> findByCategoryAndName(MenuCategory category, String name);
}