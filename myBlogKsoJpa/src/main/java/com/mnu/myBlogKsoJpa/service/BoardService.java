package com.mnu.myBlogKsoJpa.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.myBlogKsoJpa.entity.BoardEntity;
import com.mnu.myBlogKsoJpa.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    // 전체 개수 가져오기
    public int getBoardCount(String keyword) {
        return (int) boardRepository.countByKeyword(keyword);
    }
    
 // 특정 페이지의 리스트 가져오기
    public List<BoardEntity> getBoardListByPage(int start, int size, String keyword) {
        // JPA의 PageRequest는 0번부터 시작하므로 (start/size)로 페이지 번호 계산
        Pageable pageable = PageRequest.of(start / size, size, Sort.by("idx").descending());
        return boardRepository.findByKeywordPage(keyword, pageable);
    }

    public BoardEntity getBoardView(int idx) {
        return boardRepository.findById(idx).orElseThrow();
    }

    public void increaseReadCnt(int idx) {
        BoardEntity board = getBoardView(idx);
        board.setReadcnt(board.getReadcnt() + 1);
        // save() 필요 없음 (변경 감지)
    }

    public void write(BoardEntity board) {
        boardRepository.save(board);
    }

    public void modify(BoardEntity board) {
        boardRepository.save(board);
    }

    public void delete(int idx) {
        boardRepository.deleteById(idx);
    }
 // 최근 공지사항 n개 가져오기
    public List<BoardEntity> getRecentNotices(int limit) {
        // 0번째 페이지에서 limit 개수만큼, idx 내림차순 정렬
        Pageable pageable = PageRequest.of(0, limit, Sort.by("idx").descending());
        return boardRepository.findAll(pageable).getContent();
    }
}