package com.izakaya.sion.entity;

public enum OrderStatus {

	 CREATED,    // 주문 생성
	    PAID,       // 결제 완료(선택: Payment 기준으로만 봐도 됨)
	    CANCELED    // 취소
}
