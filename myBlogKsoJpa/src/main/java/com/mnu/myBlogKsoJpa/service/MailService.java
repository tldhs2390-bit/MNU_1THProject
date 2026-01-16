package com.mnu.myBlogKsoJpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendBlockMail(String email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("글/댓글 작성 제한 안내");
        msg.setText("관리자에 의해 글/댓글 작성이 제한되었습니다.");
        mailSender.send(msg);
    }

    public void sendKickMail(String email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("회원 강퇴 안내");
        msg.setText("관리자에 의해 회원 강퇴 처리되었습니다.");
        mailSender.send(msg);
    }
}