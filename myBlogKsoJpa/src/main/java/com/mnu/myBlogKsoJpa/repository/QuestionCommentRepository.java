package com.mnu.myBlogKsoJpa.repository;

import com.mnu.myBlogKsoJpa.entity.QuestionCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCommentRepository extends JpaRepository<QuestionCommentEntity, Integer> {
}