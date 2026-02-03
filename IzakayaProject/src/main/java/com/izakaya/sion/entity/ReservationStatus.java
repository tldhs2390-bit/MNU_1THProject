package com.izakaya.sion.entity;

public enum ReservationStatus {
    PENDING,     // 관리자 확인 대기
    CONFIRMED,   // 예약 확정
    REJECTED     // 예약 거절 (확장용)
}