package com.mnu.myBlogKsoJpa.domain;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class QuestionCommentDTO {
	private int cidx;
    private int qidx;
    private String userid;
    private String nickname;
    private String content;
    private String filename;
    private LocalDateTime regdate; // ← 여기 반드시 LocalDateTime
    
}
