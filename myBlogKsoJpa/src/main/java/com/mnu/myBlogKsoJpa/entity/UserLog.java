package com.mnu.myBlogKsoJpa.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_log")
@Getter
@Setter
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userid;
    private String logType;
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}