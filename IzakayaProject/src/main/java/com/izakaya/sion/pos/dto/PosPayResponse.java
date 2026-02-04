package com.izakaya.sion.pos.dto;

public class PosPayResponse {
    private Long orderId;
    private Long paymentId;
    private Long paidAmount;

    public PosPayResponse() {}

    public PosPayResponse(Long orderId, Long paymentId, Long paidAmount) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.paidAmount = paidAmount;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }

    public Long getPaidAmount() { return paidAmount; }
    public void setPaidAmount(Long paidAmount) { this.paidAmount = paidAmount; }
}