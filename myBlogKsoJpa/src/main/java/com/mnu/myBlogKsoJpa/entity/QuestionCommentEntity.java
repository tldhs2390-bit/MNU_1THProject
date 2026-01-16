package com.mnu.myBlogKsoJpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "blog_question_comment")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cidx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qidx")
    private QuestionEntity question;

    private String userid;
    private String nickname;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    private String filename;

    @CreationTimestamp
    private LocalDateTime regdate;
}