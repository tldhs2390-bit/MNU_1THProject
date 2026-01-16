package com.mnu.myBlogKsoJpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "blog_board")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String name;

    private int readcnt;

    @Column(updatable = false)
    private LocalDateTime regdate;

    @PrePersist
    protected void onCreate() {
        this.regdate = LocalDateTime.now();
    }
}