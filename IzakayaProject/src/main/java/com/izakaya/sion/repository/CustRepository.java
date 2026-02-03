package com.izakaya.sion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izakaya.sion.entity.CustEntity;

public interface CustRepository extends JpaRepository<CustEntity, Long> {
    Optional<CustEntity> findByLineUid(String lineUid);
}