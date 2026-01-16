package com.mnu.myBlogKsoJpa.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnu.myBlogKsoJpa.domain.AdminUserDTO;
import com.mnu.myBlogKsoJpa.entity.UserEntity;
import com.mnu.myBlogKsoJpa.entity.UserLog;
import com.mnu.myBlogKsoJpa.entity.UserStat;
import com.mnu.myBlogKsoJpa.repository.UserRepository;
import com.mnu.myBlogKsoJpa.repository.UserLogRepository;
import com.mnu.myBlogKsoJpa.repository.UserStatRepository;

@Service
public class AdminUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatRepository userStatRepository;

    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    private MailService mailService;

    public List<AdminUserDTO> getUserList() {

        return userRepository.findAll().stream().map(user -> {
            UserStat stat = userStatRepository
                    .findById(user.getUserid())
                    .orElse(new UserStat());

            AdminUserDTO dto = new AdminUserDTO();
            dto.setUserid(user.getUserid());
            dto.setName(user.getName());
            dto.setTel(user.getTel());
            dto.setEmail(user.getEmail());
            dto.setWriteBlocked(user.isWriteBlocked());
            dto.setKicked(user.isKicked());

            dto.setPostCount(stat.getPostCount());
            dto.setCommentCount(stat.getCommentCount());
            dto.setAdminDeletedPostCount(stat.getAdminDeletedPostCount());
            dto.setAdminDeletedCommentCount(stat.getAdminDeletedCommentCount());

            return dto;
        }).collect(Collectors.toList());
    }

    public void blockUser(String userid) {
        UserEntity user = userRepository.findById(userid).orElseThrow();

        user.setWriteBlocked(true);
        user.setBlockedAt(LocalDateTime.now());

        userLogRepository.save(makeLog(userid, "ADMIN_BLOCK", "글/댓글 작성 제한"));
        mailService.sendBlockMail(user.getEmail());
    }

    public void kickUser(String userid) {
        UserEntity user = userRepository.findById(userid).orElseThrow();

        user.setKicked(true);
        user.setKickedAt(LocalDateTime.now());

        userLogRepository.save(makeLog(userid, "ADMIN_KICK", "회원 강퇴"));
        mailService.sendKickMail(user.getEmail());
    }

    private UserLog makeLog(String userid, String type, String desc) {
        UserLog log = new UserLog();
        log.setUserid(userid);
        log.setLogType(type);
        log.setDescription(desc);
        log.setCreatedAt(LocalDateTime.now());
        return log;
    }
}