package com.mnu.myBlogKsoJpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mnu.myBlogKsoJpa.entity.UserLog;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}