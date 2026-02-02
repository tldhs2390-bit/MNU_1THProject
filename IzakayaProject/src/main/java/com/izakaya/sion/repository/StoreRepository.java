package com.izakaya.sion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izakaya.sion.entity.StoreEntity;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    List<StoreEntity> findAllByOrderByIdAsc();

    List<StoreEntity> findByCityIgnoreCaseOrderByIdAsc(String city);
}