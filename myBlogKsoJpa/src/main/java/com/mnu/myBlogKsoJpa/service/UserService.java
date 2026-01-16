package com.mnu.myBlogKsoJpa.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.myBlogKsoJpa.entity.UserEntity;
import com.mnu.myBlogKsoJpa.repository.UserRepository;
import com.mnu.myBlogKsoJpa.util.UserSHA256;

import lombok.RequiredArgsConstructor;

// ✅ CoolSMS SDK 4.3 정상 import
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    // SMS 설정
    @Value("${coolsms.apikey}")
    private String apikey;

    @Value("${coolsms.apisecret}")
    private String apisecret;

    @Value("${coolsms.fromnumber}")
    private String fromnumber;

    @Value("${coolsms.url}")
    private String url;

    // 아이디 중복 검사
    public int userIdCheck(String userid) {
        return userRepository.existsByUserid(userid) ? 1 : 0;
    }

    // 회원가입
    public void userInsert(UserEntity user) {
        user.setPasswd(UserSHA256.getSHA256(user.getPasswd()));
        userRepository.save(user);
    }

    // 로그인 시 비밀번호 조회
    public String getPassword(String userid) {
        return userRepository.findByUserid(userid)
                .map(UserEntity::getPasswd)
                .orElse(null);
    }

    // 로그인 성공 시 UserEntity 반환
    public UserEntity login(String userid) {
        return userRepository.findByUserid(userid).orElse(null);
    }

    // 최근 로그인 시간 업데이트
    public void updateLastLogin(String userid) {
        userRepository.findByUserid(userid).ifPresent(user -> {
            user.setLastTime(LocalDateTime.now());
        });
    }

    // 회원 정보 수정
    public void userModify(UserEntity user) {
        user.setPasswd(UserSHA256.getSHA256(user.getPasswd()));
        userRepository.save(user);
    }

    // 회원 삭제 (비밀번호 체크)
    public boolean deleteUser(String userid, String passwd) {
        Optional<UserEntity> userOpt = userRepository.findByUserid(userid);

        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            if (user.getPasswd().equals(UserSHA256.getSHA256(passwd))) {
                userRepository.delete(user);
                return true;
            }
        }
        return false;
    }

    // ✅ SMS 인증번호 발송
    public String sendSMS(String phoneNumber) {
        String code = randomNumber();

        DefaultMessageService messageService =
                NurigoApp.INSTANCE.initialize(apikey, apisecret, url);

        Message message = new Message();
        message.setFrom(fromnumber);
        message.setTo(phoneNumber);
        message.setText("인증번호 : " + code);

        messageService.sendOne(new SingleMessageSendingRequest(message));

        return code;
    }

    // 인증번호 생성
    private String randomNumber() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }
}