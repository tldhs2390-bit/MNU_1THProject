package com.mnu.myBlogKsoJpa.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserDTO {

    private String userid;
    private String name;
    private String tel;
    private String email;

    private boolean writeBlocked;
    private boolean kicked;

    private int postCount;
    private int commentCount;
    private int adminDeletedPostCount;
    private int adminDeletedCommentCount;
}