package com.izakaya.sion.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "menu")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 프론트의 data-cat과 매핑할 카테고리
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MenuCategory category;

    // 메뉴명
    @Column(nullable = false, length = 120)
    private String name;

    // 설명
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length = 80)
    private String highlightText;

    // 가격 텍스트(예: ¥980（税込）, ※1人前 ¥1,680（税込）)
    @Column(nullable = false, length = 80)
    private String priceText;

    // 태그(예: 人気/定番/辛口)
    @Column(length = 40)
    private String tagText;

    // 원산지 표시문
    @Lob
    @Column(columnDefinition = "TEXT")
    private String originText;

    // 알레르기 표시문
    @Lob
    @Column(columnDefinition = "TEXT")
    private String allergyText;

    // 이미지 경로(예: /images/menu/xxx.png 또는 /uploads/menu/xxx.png)
    @Column(length = 255)
    private String imageUrl;

    // 정렬(작을수록 위)
    @Column(nullable = false)
    private Integer sortOrder;

    // 노출 여부
    @Column(nullable = false)
    private Boolean visible;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (sortOrder == null) sortOrder = 9999;
        if (visible == null) visible = true;
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}