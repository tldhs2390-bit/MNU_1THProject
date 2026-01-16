package com.mnu.myBlogKsoJpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blog_question")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;

    private String userid;
    private String nickname;
    private String filename;

    @ColumnDefault("0")
    private int readcnt;

    @CreationTimestamp
    private LocalDateTime regdate;

    // 댓글과의 관계 설정 (mappedBy로 연동)
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("regdate ASC")
    private List<QuestionCommentEntity> commentList = new ArrayList<>();

    // 가상의 컬럼: 댓글 수 계산 (성능을 위해 쿼리에서 처리하거나 @Formula 사용 가능)
    @Transient
    public int getCommentCount() {
        if (commentList == null) return 0;
        return (int) commentList.stream()
                .filter(c -> !"관리자가 삭제한 댓글입니다.".equals(c.getContent()))
                .count();
    }
}