package com.mnu.myBlogKsoJpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mnu.myBlogKsoJpa.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    
    // 검색 조건이 있는 페이징 처리 (Question과 동일한 방식)
    @Query("SELECT b FROM BoardEntity b WHERE (:keyword IS NULL OR b.subject LIKE %:keyword% OR b.content LIKE %:keyword% OR b.name LIKE %:keyword%)")
    List<BoardEntity> findByKeywordPage(@Param("keyword") String keyword, Pageable pageable);

    // 전체 개수 (검색 포함)
    @Query("SELECT COUNT(b) FROM BoardEntity b WHERE (:keyword IS NULL OR b.subject LIKE %:keyword% OR b.content LIKE %:keyword% OR b.name LIKE %:keyword%)")
    long countByKeyword(@Param("keyword") String keyword);
}
