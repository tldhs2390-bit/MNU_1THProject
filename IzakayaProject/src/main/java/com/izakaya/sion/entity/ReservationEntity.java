package com.izakaya.sion.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reservation", schema = "izakaya",
       indexes = {
           @Index(name="idx_resv_store_status", columnList="store_id, status"),
           @Index(name="idx_resv_store_visit", columnList="store_id, visit_at")
       })
@Getter @Setter
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="store_id", nullable = false)
    private StoreEntity store;

    @Column(name="customer_id", nullable = false)
    private Long customerId;

    @Column(name="customer_name", nullable = false, length = 60)
    private String customerName;

    // ✅✅✅ [추가] DB NOT NULL 컬럼 대응
    @Column(name="phone", nullable = false, length = 30)
    private String phone;

    @Column(name="visit_at", nullable = false)
    private LocalDateTime visitAt;

    @Column(nullable = false)
    private Integer headcount;

    @Column(name="req_note", length = 500)
    private String reqNote;

    @Column(name="line_user_id", length = 80)
    private String lineUserId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationStatus status = ReservationStatus.PENDING;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}