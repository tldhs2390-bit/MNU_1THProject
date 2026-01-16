package com.mnu.myBlogKsoJpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blog_user")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @Column(unique = true)
    private String userid;

    private String name;

    private String passwd;

    private String tel;

    private String email;

    @Column(updatable = false)
    private LocalDateTime firstTime;

    private LocalDateTime lastTime;
    @Transient
    private int rate;
    @Transient
    private boolean admin;

    @PrePersist
    protected void onCreate() {
        this.firstTime = LocalDateTime.now();
    }
}