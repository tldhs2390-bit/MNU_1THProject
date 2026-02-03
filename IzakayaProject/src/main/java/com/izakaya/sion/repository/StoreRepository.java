package com.izakaya.sion.repository;

import com.izakaya.sion.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    List<StoreEntity> findAllByOrderByIdAsc();

    List<StoreEntity> findByCityIgnoreCaseOrderByIdAsc(String city);
}