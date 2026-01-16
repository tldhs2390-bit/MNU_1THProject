package com.mnu.myBlogKsoJpa.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mnu.myBlogKsoJpa.domain.BoardDTO;
import com.mnu.myBlogKsoJpa.entity.BoardEntity;
import com.mnu.myBlogKsoJpa.service.BoardService;
import com.mnu.myBlogKsoJpa.util.PageIndex;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board_list")
    public String boardList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "keyword", required = false) String keyword,
            HttpSession session, // 세션 필요시 추가
            Model model) {

        int pageSize = 10;
        // 1. 전체 개수 구하기
        int totalCount = boardService.getBoardCount(keyword);

        // 2. 전체 페이지 수 계산
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        if (totalPage == 0) totalPage = 1;
        if (page < 1) page = 1;
        if (page > totalPage) page = totalPage;

        // 3. 시작 지점 계산 및 리스트 조회
        int start = (page - 1) * pageSize;
        List<BoardEntity> list = boardService.getBoardListByPage(start, pageSize, keyword);

        // 4. 목록 번호 시작값 계산 (내림차순 번호용)
        int listcountStart = totalCount - start;

        // 5. PageIndex 유틸리티를 사용하여 HTML 생성 (기존 디자인 복구)
        String pageHtml = (keyword != null && !keyword.trim().isEmpty())
                ? PageIndex.pageListHan(page, totalPage, "board_list", pageSize, "keyword", keyword)
                : PageIndex.pageList(page, totalPage, "board_list", pageSize);

        model.addAttribute("list", list);
        model.addAttribute("page", page);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("listcountStart", listcountStart);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagelist", pageHtml); // HTML 태그 전달

        return "board/board_list";
    }
    // 상세
    @GetMapping("/board_view")
    public String boardView(
    		@RequestParam("idx") int idx,
            @RequestParam(value="page", defaultValue="1") int page,
            HttpSession session,
            Model model) {

        String sessionKey = "board_view_" + idx;
        Long lastViewTime = (Long) session.getAttribute(sessionKey);
        long now = System.currentTimeMillis();

        if (lastViewTime == null || now - lastViewTime > 3600_000) {
            boardService.increaseReadCnt(idx);
            session.setAttribute(sessionKey, now);
        }

        model.addAttribute("board", boardService.getBoardView(idx));
        model.addAttribute("page", page);

        return "board/board_view";
    }

    // 글쓰기 폼
    @GetMapping("/board_write")
    public String writeForm(
    		@RequestParam(value="page", defaultValue="1") int page,
            Model model) {

        model.addAttribute("page", page);
        return "board/board_write";
    }

    // 글쓰기 처리
    @PostMapping("/board_write")
    public String write(
            BoardDTO dto,
            @RequestParam(value="page", defaultValue="1") int page) {

        BoardEntity entity = BoardEntity.builder()
                .subject(dto.getSubject())
                .content(dto.getContent())
                .name("관리자")
                .build();

        boardService.write(entity);

        return "redirect:/board/board_list?page=" + page;
    }

    // 수정 폼
    @GetMapping("/board_modify")
    public String modifyForm(
    		@RequestParam("idx") int idx,
    		@RequestParam(value="page", defaultValue="1") int page,
            Model model) {

        model.addAttribute("board", boardService.getBoardView(idx));
        model.addAttribute("page", page);

        return "board/board_modify";
    }

    // 수정 처리
    @PostMapping("/board_modify")
    public String modify(
            BoardDTO dto,
            @RequestParam(value="page", defaultValue="1") int page) {

        BoardEntity entity = boardService.getBoardView(dto.getIdx());
        entity.setSubject(dto.getSubject());
        entity.setContent(dto.getContent());

        boardService.modify(entity);

        return "redirect:/board/board_view?idx=" + dto.getIdx() + "&page=" + page;
    }

    // 삭제
    @PostMapping("/board_delete")
    public String delete(@RequestParam("idx") int idx) {
        boardService.delete(idx);
        return "redirect:/board/board_list";
    }
}