package com.mnu.myBlogKsoJpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mnu.myBlogKsoJpa.entity.UserStat;

public interface UserStatRepository extends JpaRepository<UserStat, String> {
}