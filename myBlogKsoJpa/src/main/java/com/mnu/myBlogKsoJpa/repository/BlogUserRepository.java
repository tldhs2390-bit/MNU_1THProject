package com.mnu.myBlogKsoJpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mnu.myBlogKsoJpa.entity.UserEntity;

public interface BlogUserRepository extends JpaRepository<UserEntity, String> {
}