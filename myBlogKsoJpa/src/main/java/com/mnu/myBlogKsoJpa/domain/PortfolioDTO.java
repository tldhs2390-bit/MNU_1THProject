package com.mnu.myBlogKsoJpa.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PortfolioDTO {

    private Integer idx;
    private String title;
    private String content;
    private String filename;
    private String name;
    private int readcnt;
    private LocalDateTime regdate;
}