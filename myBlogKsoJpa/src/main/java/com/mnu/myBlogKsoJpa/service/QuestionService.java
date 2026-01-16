package com.mnu.myBlogKsoJpa.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mnu.myBlogKsoJpa.entity.QuestionCommentEntity;
import com.mnu.myBlogKsoJpa.entity.QuestionEntity;
import com.mnu.myBlogKsoJpa.repository.QuestionCommentRepository;
import com.mnu.myBlogKsoJpa.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionCommentRepository commentRepository;

    public int getQuestionCount(String keyword) {
        return (int) questionRepository.countByKeyword(keyword);
    }

    public List<QuestionEntity> getQuestionListByPage(int start, int pageSize, String keyword) {
        int pageNumber = start / pageSize;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("idx").descending());
        return questionRepository.findByKeyword(keyword, pageRequest).getContent();
    }

    public QuestionEntity getQuestionView(int idx) {
        return questionRepository.findById(idx).orElse(null);
    }

    public void updateReadCnt(int idx) {
        QuestionEntity question = questionRepository.findById(idx).orElse(null);
        if (question != null) {
            question.setReadcnt(question.getReadcnt() + 1);
        }
    }

    public void questionWrite(QuestionEntity dto, MultipartFile file, String uploadPath) throws IOException {
        if (file != null && !file.isEmpty()) {
            String saveFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();
            file.transferTo(new File(uploadPath, saveFileName));
            dto.setFilename(saveFileName);
        }
        questionRepository.save(dto);
    }

    public void questionModify(QuestionEntity dto, MultipartFile file, String uploadPath) throws IOException {
        QuestionEntity dbDto = questionRepository.findById(dto.getIdx()).orElseThrow();
        dbDto.setTitle(dto.getTitle());
        dbDto.setContent(dto.getContent());
        if (file != null && !file.isEmpty()) {
            String saveFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath, saveFileName));
            dbDto.setFilename(saveFileName);
        }
        questionRepository.save(dbDto);
    }

    public void questionDelete(int idx) {
        questionRepository.deleteById(idx);
    }

    public void commentWrite(QuestionCommentEntity dto, MultipartFile file, String uploadPath) throws IOException {
        if (file != null && !file.isEmpty()) {
            String saveFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath, saveFileName));
            dto.setFilename(saveFileName);
        }
        commentRepository.save(dto);
    }

    public void commentDelete(int cidx) {
        commentRepository.deleteById(cidx);
    }

    public void markAsDeletedByAdmin(int idx) {
        QuestionEntity question = questionRepository.findById(idx).orElse(null);
        if (question != null) {
            question.setTitle("관리자가 삭제한 글입니다.");
            question.setContent("관리자가 삭제한 글입니다.");
            question.setNickname("관리자");
        }
    }

    public void markCommentAsDeletedByAdmin(int cidx) {
        QuestionCommentEntity comment = commentRepository.findById(cidx).orElse(null);
        if (comment != null) {
            comment.setContent("관리자가 삭제한 댓글입니다.");
            comment.setNickname("관리자");
        }
    }
 // 댓글 하나 가져오기 (추가)
    public QuestionCommentEntity getComment(int cidx) {
        return commentRepository.findById(cidx).orElse(null);
    }
 // 최근 Q/A n개 가져오기
    public List<QuestionEntity> getRecentQuestions(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by("idx").descending());
        // findByKeyword 메서드를 활용하거나 findAll을 사용합니다.
        return questionRepository.findAll(pageable).getContent();
    }
}