package com.mnu.myBlogKsoJpa.repository;

import com.mnu.myBlogKsoJpa.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {
    
    // 검색 조건이 있는 페이징 처리
    @Query("SELECT q FROM QuestionEntity q WHERE (:keyword IS NULL OR q.title LIKE %:keyword% OR q.content LIKE %:keyword% OR q.nickname LIKE %:keyword%)")
    Page<QuestionEntity> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // 전체 개수 (검색 포함)
    @Query("SELECT COUNT(q) FROM QuestionEntity q WHERE (:keyword IS NULL OR q.title LIKE %:keyword% OR q.content LIKE %:keyword% OR q.nickname LIKE %:keyword%)")
    long countByKeyword(@Param("keyword") String keyword);

    // 최근 글 목록
    List<QuestionEntity> findTop5ByOrderByRegdateDesc();
}