package com.mnu.myBlogKsoJpa.domain;

import lombok.Data;

@Data
public class UserDTO {

	private String userid;
	private String name;
	private String passwd;
	private String tel;
	private String email;
	private String first_time;
	private String last_time;
	private int rate;//회원등급(0,1,2,3,4,5)
	private boolean admin; // 관리자 여부
	private boolean isAdmin;
}
