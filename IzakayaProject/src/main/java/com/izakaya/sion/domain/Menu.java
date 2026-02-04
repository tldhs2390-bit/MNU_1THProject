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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MenuCategory category;

    @Column(nullable = false, length = 120)
    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 80)
    private String highlightText;

    // ✅✅✅ [추가] 결제/집계용 숫자 가격
    @Column(nullable = false)
    private Long price;

    // 가격 텍스트(표시용)
    @Column(nullable = false, length = 80)
    private String priceText;

    @Column(length = 40)
    private String tagText;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String originText;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String allergyText;

    @Column(length = 255)
    private String imageUrl;

    @Column(nullable = false)
    private Integer sortOrder;

    @Column(nullable = false)
    private Boolean visible;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (sortOrder == null) sortOrder = 9999;
        if (visible == null) visible = true;
        if (createdAt == null) createdAt = LocalDateTime.now();

        // ✅ null 방지 기본값
        if (price == null) price = 0L;
        if (priceText == null) priceText = "¥0";
    }
}