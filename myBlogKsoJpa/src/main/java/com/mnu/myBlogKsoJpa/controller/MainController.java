package com.mnu.myBlogKsoJpa.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mnu.myBlogKsoJpa.entity.BoardEntity;
import com.mnu.myBlogKsoJpa.entity.QuestionEntity;
import com.mnu.myBlogKsoJpa.service.BoardService;
import com.mnu.myBlogKsoJpa.service.QuestionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final BoardService boardService;
    private final QuestionService questionService;
 
    @GetMapping("/")
    public String index(Model model) {
        // 최근 공지사항 3개 (엔티티 리스트)
        List<BoardEntity> recentNotices = boardService.getRecentNotices(3);
        model.addAttribute("recentNotices", recentNotices);

        // 최근 Q/A 3개 (엔티티 리스트)
        List<QuestionEntity> recentQuestions = questionService.getRecentQuestions(3);
        model.addAttribute("recentQuestions", recentQuestions);

        return "index";
    }
}
