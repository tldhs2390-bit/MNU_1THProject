package com.mnu.myBlogKsoJpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_stat")
@Getter
@Setter
public class UserStat {

    @Id
    private String userid;

    private int postCount;
    private int commentCount;
    private int adminDeletedPostCount;
    private int adminDeletedCommentCount;
}