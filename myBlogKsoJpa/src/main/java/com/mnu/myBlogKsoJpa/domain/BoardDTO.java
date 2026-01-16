package com.mnu.myBlogKsoJpa.domain;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class BoardDTO {
	private int idx;
    private String subject;
    private String content;
    private String name;
    private int readcnt;
    private LocalDateTime regdate;
}
