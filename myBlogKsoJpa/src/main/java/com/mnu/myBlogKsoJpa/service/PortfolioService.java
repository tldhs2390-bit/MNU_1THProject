package com.mnu.myBlogKsoJpa.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.myBlogKsoJpa.entity.PortfolioEntity;
import com.mnu.myBlogKsoJpa.repository.PortfolioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    // 전체 개수 가져오기
    public int getPortfolioCount(String keyword) {
        return (int) portfolioRepository.countByKeyword(keyword);
    }

    // 특정 페이지의 리스트 가져오기
    public List<PortfolioEntity> getPortfolioListByPage(int start, int size, String keyword) {
        // PageRequest.of(페이지번호, 사이즈, 정렬) - 페이지번호는 0부터 시작
        Pageable pageable = PageRequest.of(start / size, size, Sort.by("idx").descending());
        return portfolioRepository.findByKeywordPage(keyword, pageable);
    }

    // 상세
    public PortfolioEntity getView(int idx) {
        return portfolioRepository.findById(idx).orElseThrow();
    }

    // 조회수 증가
    public void increaseReadCnt(int idx) {
        PortfolioEntity entity = getView(idx);
        entity.setReadcnt(entity.getReadcnt() + 1);
        // save() 호출 안 해도 됨 (Dirty Checking)
    }

    // 글 작성
    public void write(PortfolioEntity entity) {
        portfolioRepository.save(entity);
    }

    // 글 수정
    public void modify(PortfolioEntity entity) {
        portfolioRepository.save(entity);
    }

    // 글 삭제
    public void delete(int idx) {
        portfolioRepository.deleteById(idx);
    }
}