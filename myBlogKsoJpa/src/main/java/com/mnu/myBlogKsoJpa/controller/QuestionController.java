package com.mnu.myBlogKsoJpa.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mnu.myBlogKsoJpa.entity.QuestionCommentEntity;
import com.mnu.myBlogKsoJpa.entity.QuestionEntity;
import com.mnu.myBlogKsoJpa.entity.UserEntity; // ⭐ UserEntity 사용
import com.mnu.myBlogKsoJpa.service.QuestionService;
import com.mnu.myBlogKsoJpa.util.PageIndex;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final String uploadDir = "/Users/gimsion/upload/question/";

    // =================== 글 목록 ===================
    @GetMapping("question_list")
    public String questionList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "keyword", required = false) String keyword,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {

        // 세션에서 UserEntity로 꺼내어 캐스팅 오류 방지
        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user == null){
            redirectAttributes.addFlashAttribute("loginAlert", "로그인이 필요한 게시판입니다!");
            return "redirect:/user/user_login";
        }

        int pageSize = 10;
        int totalCount = questionService.getQuestionCount(keyword);

        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        if(totalPage == 0) totalPage = 1;
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;

        int start = (page - 1) * pageSize;
        List<QuestionEntity> list = questionService.getQuestionListByPage(start, pageSize, keyword);

        int listcountStart = totalCount - start;

        model.addAttribute("list", list);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("listcountStart", listcountStart);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("keyword", keyword);

        String pageHtml = (keyword != null && !keyword.trim().isEmpty())
                ? PageIndex.pageListHan(page, totalPage, "question_list", pageSize, "keyword", keyword)
                : PageIndex.pageList(page, totalPage, "question_list", pageSize);

        model.addAttribute("pagelist", pageHtml);

        return "question/question_list";
    }

    // =================== 글 보기 ===================
    @GetMapping("question_view")
    public String questionView(
            @RequestParam(value = "idx") int idx,
            @RequestParam(value = "page", defaultValue = "1") int page,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {

        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user == null){
            redirectAttributes.addFlashAttribute("loginAlert", "로그인이 필요한 게시판입니다!");
            return "redirect:/user/user_login";
        }

        String sessionKey = "question_view_" + idx;
        Long lastViewTime = (Long) session.getAttribute(sessionKey);
        long now = System.currentTimeMillis();

        if(lastViewTime == null || now - lastViewTime > 3600_000){
            questionService.updateReadCnt(idx);
            session.setAttribute(sessionKey, now);
        }

        QuestionEntity dto = questionService.getQuestionView(idx);
        model.addAttribute("dto", dto);
        model.addAttribute("commentList", dto.getCommentList());
        model.addAttribute("page", page);

        return "question/question_view";
    }

    // =================== 글 작성 ===================
    @GetMapping("question_write")
    public String questionWriteForm(
            @RequestParam(value = "page", defaultValue = "1") int page,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {

        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user == null){
            redirectAttributes.addFlashAttribute("loginAlert", "로그인이 필요한 게시판입니다!");
            return "redirect:/user/user_login";
        }

        model.addAttribute("page", page);
        return "question/question_write";
    }

    @PostMapping("question_write")
    public String questionWrite(
            @ModelAttribute QuestionEntity dto,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpSession session) throws IOException {

        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user == null){
            session.setAttribute("loginAlert", "로그인이 필요한 게시판입니다!");
            return "redirect:/user/user_login";
        }

        // 관리자 여부 체크 (isAdmin() 메서드 또는 아이디 비교)
        if(user.isAdmin() || "admin".equals(user.getUserid())) {
            dto.setUserid("admin");
            dto.setNickname("관리자"); // DB에도 관리자로 저장
        } else {
            dto.setUserid(user.getUserid());
            dto.setNickname(user.getUserid()); // 일반 사용자는 닉네임 필드에도 아이디 저장
        }

        questionService.questionWrite(dto, file, uploadDir);
        return "redirect:/question/question_list?page=" + page;
    }

    // =================== 글 수정 ===================
    @GetMapping("question_modify")
    public String questionModifyForm(
            @RequestParam(value = "idx") int idx,
            @RequestParam(value = "page", defaultValue = "1") int page,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {

        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user == null){
            redirectAttributes.addFlashAttribute("loginAlert", "로그인이 필요한 게시판입니다!");
            return "redirect:/user/user_login";
        }

        QuestionEntity dto = questionService.getQuestionView(idx);
        
        // 작성자 본인 확인 또는 관리자 확인
        if(!dto.getUserid().equals(user.getUserid()) && !user.isAdmin()){
            redirectAttributes.addFlashAttribute("loginAlert", "본인이 작성한 글만 수정할 수 있습니다!");
            return "redirect:/question/question_view?idx=" + idx + "&page=" + page;
        }

        model.addAttribute("dto", dto);
        model.addAttribute("page", page);
        return "question/question_modify";
    }

    @PostMapping("question_modify")
    public String questionModify(
            @ModelAttribute QuestionEntity dto,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpSession session) throws IOException {

        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user == null) return "redirect:/user/user_login";

        questionService.questionModify(dto, file, uploadDir);
        return "redirect:/question/question_view?idx=" + dto.getIdx() + "&page=" + page;
    }

    // =================== 글 삭제 ===================
 // =================== 게시글 삭제 POST ===================
    @PostMapping("question_delete")
    public String questionDelete(
            @RequestParam(value = "idx") int idx,
            @RequestParam(value = "page", defaultValue = "1") int page,
            HttpSession session) {

        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user == null) return "redirect:/user/user_login";

        QuestionEntity dto = questionService.getQuestionView(idx);
        
        if(user.isAdmin() || dto.getUserid().equals(user.getUserid())) {
            if(user.isAdmin() && !dto.getUserid().equals(user.getUserid())) {
                // [중요] 관리자가 삭제할 때 제목뿐만 아니라 userid도 'admin'으로 변경해야 리스트에서 '관리자'로 뜹니다.
                questionService.markAsDeletedByAdmin(idx); 
                // 만약 서비스에서 userid를 안 바꾼다면 여기서 명시적으로 변경하는 로직이 서비스 내부에 포함되어야 합니다.
            } else {
                questionService.questionDelete(idx);
            }
            return "redirect:/question/question_list?page=" + page;
        }

        return "redirect:/question/question_view?idx=" + idx;
    }

    // =================== 댓글 작성 ===================
    @PostMapping("comment_write")
    public String commentWrite(
            @ModelAttribute QuestionCommentEntity dto,
            @RequestParam(value = "qidx") int qidx,
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpSession session) throws IOException {

        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user == null) return "redirect:/user/user_login";
        if(user.isAdmin() || "admin".equals(user.getUserid())) {
            dto.setUserid("admin");
            dto.setNickname("관리자");
        } else {
            dto.setUserid(user.getUserid());
            dto.setNickname(user.getUserid());
        }

        QuestionEntity question = questionService.getQuestionView(qidx);
        dto.setQuestion(question);
        dto.setUserid(user.getUserid());
        dto.setNickname(user.getUserid());

        questionService.commentWrite(dto, file, uploadDir);
        return "redirect:/question/question_view?idx=" + qidx;
    }

    // =================== 댓글 삭제 ===================
    @PostMapping("comment_delete")
    public String commentDelete(
            @RequestParam(value = "cidx") int cidx,
            @RequestParam(value = "qidx") int qidx,
            HttpSession session) {

        // 1. 로그인 확인
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null) return "redirect:/user/user_login";

        // 2. 서비스 호출 (서비스 내부에서 권한 체크를 하거나, 컨트롤러에서 가져와서 체크)
        // 여기서는 컨트롤러에서 명확하게 처리하는 방식을 보여드립니다.
        QuestionCommentEntity comment = questionService.getComment(cidx); // 서비스에 getComment 추가 필요

        if (comment != null) {
            // 관리자이거나 작성자인 경우
            boolean isAdmin = user.isAdmin() || user.getUserid().equals("admin");
            boolean isAuthor = comment.getUserid().equals(user.getUserid());

            if (isAdmin || isAuthor) {
                if (isAdmin && !isAuthor) {
                    // 관리자가 삭제할 때 (내용만 블라인드)
                    questionService.markCommentAsDeletedByAdmin(cidx);
                } else {
                    // 본인이 삭제할 때 (실제 삭제)
                    questionService.commentDelete(cidx);
                }
            }
        }

        return "redirect:/question/question_view?idx=" + qidx;
    }
}