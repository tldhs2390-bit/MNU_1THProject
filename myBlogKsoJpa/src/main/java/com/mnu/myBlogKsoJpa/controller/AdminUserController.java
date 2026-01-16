package com.mnu.myBlogKsoJpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mnu.myBlogKsoJpa.service.AdminUserService;

@Controller
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    // 관리자 회원 목록
    @GetMapping("/admin/user_list")
    public String user_list(Model model) {
        model.addAttribute("users", adminUserService.getUserList());
        return "admin/user_list";
    }

    // 글/댓글 작성 제한
    @PostMapping("/admin/user_block")
    public String user_block(@RequestParam("userid") String userid) {
        adminUserService.blockUser(userid);
        return "redirect:/admin/user_list";
    }

    // 회원 강퇴
    @PostMapping("/admin/user_kick")
    public String user_kick(@RequestParam("userid") String userid) {
        adminUserService.kickUser(userid);
        return "redirect:/admin/user_list";
    }
}