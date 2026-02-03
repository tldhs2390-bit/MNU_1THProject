package com.izakaya.sion.repository;

import com.izakaya.sion.domain.Menu;
import com.izakaya.sion.domain.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByOrderBySortOrderAscIdDesc();

    List<Menu> findAllByVisibleTrueOrderBySortOrderAscIdDesc();

    List<Menu> findAllByVisibleTrueAndCategoryOrderBySortOrderAscIdDesc(MenuCategory category);
}