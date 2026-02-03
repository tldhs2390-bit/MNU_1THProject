package com.izakaya.sion.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "resv")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resv_id")
    private Long resvId;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "cust_id", nullable = false)
    private Long custId;

    @Column(name = "resv_at", nullable = false)
    private LocalDateTime resvAt;

    @Column(name = "party_cnt", nullable = false)
    private int partyCnt;

    @Column(name = "req_note", length = 500)
    private String reqNote;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    /* ====== 예약 생성 (PENDING) ====== */
    public static ReservationEntity createPending(
            Long storeId,
            Long custId,
            LocalDateTime resvAt,
            int partyCnt,
            String reqNote
    ) {
        ReservationEntity e = new ReservationEntity();
        e.storeId = storeId;
        e.custId = custId;
        e.resvAt = resvAt;
        e.partyCnt = partyCnt;
        e.reqNote = reqNote;
        e.status = ReservationStatus.PENDING;
        return e;
    }

    /* ====== 예약 확정 ====== */
    public void confirm() {
        this.status = ReservationStatus.CONFIRMED;
    }

    /* ====== 예약 거절 ====== */
    public void reject() {
        this.status = ReservationStatus.REJECTED;
    }
}