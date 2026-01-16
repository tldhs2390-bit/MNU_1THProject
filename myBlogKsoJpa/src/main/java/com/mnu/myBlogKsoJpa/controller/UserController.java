package com.mnu.myBlogKsoJpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mnu.myBlogKsoJpa.entity.UserEntity;
import com.mnu.myBlogKsoJpa.service.UserService;
import com.mnu.myBlogKsoJpa.util.UserSHA256;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /* =========================
       로그인 폼
    ========================= */
    @GetMapping("user_login")
    public String loginForm(HttpSession session) {
        return session.getAttribute("user") == null
                ? "user/user_login"
                : "redirect:/";
    }

    /* =========================
       로그인 처리
    ========================= */
    @PostMapping("user_login")
    public String login(UserEntity input,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {

        // 관리자 하드코딩 로그인
        if ("admin".equals(input.getUserid()) && "1234".equals(input.getPasswd())) {
            UserEntity admin = UserEntity.builder()
                    .userid("admin")
                    .name("관리자")
                    .admin(true)
                    .build();

            HttpSession session = request.getSession();
            session.setAttribute("user", admin);
            session.setMaxInactiveInterval(1800);

            redirectAttributes.addFlashAttribute("row", 1);
            return "redirect:/";
        }

        // 일반 회원 로그인
        String dbPass = userService.getPassword(input.getUserid());

        if (dbPass == null) {
            redirectAttributes.addFlashAttribute("row", -1); // 아이디 없음
            return "redirect:/";
        }

        if (dbPass.equals(UserSHA256.getSHA256(input.getPasswd()))) {
            UserEntity loginUser = userService.login(input.getUserid());

            HttpSession session = request.getSession();
            session.setAttribute("user", loginUser);
            session.setMaxInactiveInterval(1800);

            userService.updateLastLogin(input.getUserid());

            redirectAttributes.addFlashAttribute("row", 1); // 성공
        } else {
            redirectAttributes.addFlashAttribute("row", 0); // 비밀번호 오류
        }

        return "redirect:/";
    }
 // 약관 동의 페이지
    @GetMapping("user_terms")
    public String userTerms() {
        return "user/user_terms"; // templates/user/user_terms.html
    }

    /* =========================
       회원가입 처리
    ========================= */
  //회원 가입폼
  	@RequestMapping("/user_insert")
  	public String userInsert() {
  	    return "user/user_insert"; // templates/User/user_insert.html
  	}
  	@PostMapping("user_insert")
  	public String insert(UserEntity user, RedirectAttributes redirectAttributes) {
  	    // 비밀번호 암호화 + 저장
  	    userService.userInsert(user);

  	    // 처리 결과를 그냥 1로 표시
  	    redirectAttributes.addFlashAttribute("row", 1);

  	    return "redirect:/";  // 회원가입 완료 후 홈으로
  	}

    /* =========================
       ID 중복 체크
    ========================= */
  	@PostMapping("user_idCheck")
  	@ResponseBody
  	public String idCheck(@RequestParam("userid") String userid) {
  	    return String.valueOf(userService.userIdCheck(userid));
  	}
  //본인인증(email 또는 SMS)
  	@ResponseBody
  	@PostMapping("user_sms")
  	public String smsSend(@RequestParam("tel") String tel) {
  		
  		String tempNum = userService.sendSMS(tel);
  		
  		return tempNum;
  	}

  	/* =========================
    회원 탈퇴 페이지 이동 (GET)
 ========================= */
 @GetMapping("user_delete")
 public String userDeleteForm(HttpSession session) {
     UserEntity user = (UserEntity) session.getAttribute("user");
     if (user == null) return "redirect:/user/user_login";
     
     return "user/user_delete"; // 작성하신 HTML 파일명
 }

 /* =========================
    회원 탈퇴 실제 처리 (POST)
 ========================= */
 @PostMapping("user_delete")
 public String userDeleteProcess(@RequestParam("passwd") String passwd,
                                 HttpSession session,
                                 Model model) {
     UserEntity user = (UserEntity) session.getAttribute("user");
     if (user == null) return "redirect:/user/user_login";

     // 서비스에서 비밀번호 체크 및 삭제 (성공 시 true)
     boolean ok = userService.deleteUser(user.getUserid(), passwd);

     if (ok) {
         session.invalidate(); // 탈퇴 성공 시 로그아웃
         model.addAttribute("deleteResult", 1);
     } else {
         model.addAttribute("deleteResult", 0);
     }

     return "user/user_delete"; // 다시 같은 페이지로 가서 결과 스크립트 실행
 }
 

    /* =========================
       로그아웃
    ========================= */
    @PostMapping("user_logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}