package com.mnu.myBlogKsoJpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mnu.myBlogKsoJpa.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    boolean existsByUserid(String userid);

    Optional<UserEntity> findByUserid(String userid);
}
