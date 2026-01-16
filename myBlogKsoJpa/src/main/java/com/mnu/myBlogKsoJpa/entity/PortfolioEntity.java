package com.mnu.myBlogKsoJpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blog_portfolio")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String filename;

    private String name;

    private int readcnt;

    @Column(updatable = false)
    private LocalDateTime regdate;

    @PrePersist
    protected void onCreate() {
        this.regdate = LocalDateTime.now();
    }
}