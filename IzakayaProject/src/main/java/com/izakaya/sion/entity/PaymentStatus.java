package com.izakaya.sion.entity;

public enum PaymentStatus {
	PENDING,  // 결제 시도 전/대기
    PAID,     // 결제 완료
    CANCELED  // 결제 취소/환불
}
