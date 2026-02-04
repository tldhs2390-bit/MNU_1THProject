package com.izakaya.sion.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "store", schema = "izakaya")
public class StoreEntity {

	// StoreEntity.java 내부에 추가
	@Transient
	private Long todaySales = 0L;

	@Transient
	private Long reservationCount = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city; // TOKYO / OSAKA / FUKUOKA
    private String name; // 銀座, 新宿 ...

    private Double lat;
    private Double lng;
}