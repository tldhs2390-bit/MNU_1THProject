package com.mnu.myBlogKsoJpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "email_auth")
@Getter
@Setter
public class EmailAuth {

    @Id
    private String userid;

    private String authCode;
    private LocalDateTime expiredAt;
    private boolean verified;
}
