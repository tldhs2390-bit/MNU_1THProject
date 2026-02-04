package com.izakaya.sion.entity;

public enum ReservationStatus {
    PENDING,    // 승인 대기
    ACCEPTED,   // 수락
    REJECTED,   // 거절
    CANCELED    // 취소 (선택)
}