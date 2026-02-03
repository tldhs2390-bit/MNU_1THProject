package com.izakaya.sion.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "store", schema = "izakaya")
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city; // TOKYO / OSAKA / FUKUOKA
    private String name; // 銀座, 新宿 ...

    private Double lat;
    private Double lng;
}