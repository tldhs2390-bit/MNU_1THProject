package com.mnu.myBlogKsoJpa.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class QuestionDTO {
	private int idx;
    private String title;
    private String content;
    private MultipartFile file;
    private String nickname;
    private int readcnt;
    private LocalDateTime regdate; 
    private String filename;  
    private List<QuestionCommentDTO> commentList; // 댓글 포함
    private String userid;  // 본인 확인용
    private int commentCount;
}
