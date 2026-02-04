package com.izakaya.sion.admin.dto;

import java.time.LocalDateTime;

public interface PendingReservationView {
    Long getId();
    Integer getHeadcount();
    LocalDateTime getVisitAt();
}