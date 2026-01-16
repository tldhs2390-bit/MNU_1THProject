package com.mnu.myBlogKsoJpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mnu.myBlogKsoJpa.entity.PortfolioEntity;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {
    
    // 검색 조건이 있는 페이징 처리 (제목, 내용, 작성자 등 검색 범위 확장 가능)
    @Query("SELECT p FROM PortfolioEntity p WHERE (:keyword IS NULL OR p.title LIKE %:keyword%)")
    List<PortfolioEntity> findByKeywordPage(@Param("keyword") String keyword, Pageable pageable);

    // 전체 개수 (검색 포함)
    @Query("SELECT COUNT(p) FROM PortfolioEntity p WHERE (:keyword IS NULL OR p.title LIKE %:keyword%)")
    long countByKeyword(@Param("keyword") String keyword);
}