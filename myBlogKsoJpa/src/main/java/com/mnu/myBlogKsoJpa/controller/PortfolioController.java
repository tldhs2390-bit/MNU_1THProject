package com.mnu.myBlogKsoJpa.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mnu.myBlogKsoJpa.entity.PortfolioEntity;
import com.mnu.myBlogKsoJpa.service.PortfolioService;
import com.mnu.myBlogKsoJpa.util.PageIndex;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/portfolio_list")
    public String portfolioList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        int pageSize = 10;

        // 1. 전체 개수 구하기
        int totalCount = portfolioService.getPortfolioCount(keyword);

        // 2. 전체 페이지 수 및 페이지 보정 계산
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        if (totalPage == 0) totalPage = 1;
        if (page < 1) page = 1;
        if (page > totalPage) page = totalPage;

        // 3. 시작 지점(offset) 계산 및 리스트 조회
        int start = (page - 1) * pageSize;
        List<PortfolioEntity> list = portfolioService.getPortfolioListByPage(start, pageSize, keyword);

        // 4. 역순 번호 출력을 위한 시작값 계산
        int listcount = totalCount - start;

        // 5. PageIndex 유틸리티를 사용하여 HTML 문자열 생성
        String pageHtml = (keyword != null && !keyword.trim().isEmpty())
                ? PageIndex.pageListHan(page, totalPage, "portfolio_list", pageSize, "keyword", keyword)
                : PageIndex.pageList(page, totalPage, "portfolio_list", pageSize);

        model.addAttribute("list", list);
        model.addAttribute("page", page);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("listcount", listcount); // HTML에서 사용 중인 변수명
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagelist", pageHtml); // th:utext용

        return "portfolio/portfolio_list";
    }
    // 상세
    @GetMapping("/portfolio_view")
    public String view(
    		@RequestParam("idx") int idx,
    		@RequestParam(value="page", defaultValue="1") int page,
            HttpSession session,
            Model model) {

        String key = "portfolio_view_" + idx;
        Long last = (Long) session.getAttribute(key);
        long now = System.currentTimeMillis();

        if (last == null || now - last > 3600_000) {
            portfolioService.increaseReadCnt(idx);
            session.setAttribute(key, now);
        }

        model.addAttribute("portfolio", portfolioService.getView(idx));
        model.addAttribute("page", page);

        return "portfolio/portfolio_view";
    }

    // 글쓰기 폼
    @GetMapping("/portfolio_write")
    public String writeForm(@RequestParam(value="page", defaultValue="1") int page,
                            Model model) {
        model.addAttribute("page", page);
        return "portfolio/portfolio_write";
    }

    // 글쓰기 처리
    @PostMapping("/portfolio_write")
    public String write(
            PortfolioEntity entity,
            @RequestParam(value="file", required = false) MultipartFile file,
            @RequestParam(value="page", defaultValue="1") int page) throws IOException {

        entity.setName("관리자");

        if (file != null && !file.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String ext = file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf("."));
            String saveName = uuid + ext;

            File saveFile = new File(uploadPath, saveName);
            file.transferTo(saveFile);

            entity.setFilename(saveName);
        }

        portfolioService.write(entity);

        return "redirect:/portfolio/portfolio_list?page=" + page;
    }

    // 수정 폼
    @GetMapping("/portfolio_modify")
    public String modifyForm(
            @RequestParam("idx") int idx,
            @RequestParam(value="page", defaultValue="1") int page,
            Model model) {

        PortfolioEntity entity = portfolioService.getView(idx);

        model.addAttribute("portfolio", entity);
        model.addAttribute("page", page);

        return "portfolio/portfolio_modify"; // templates/portfolio/portfolio_modify.html
    }
    // 수정 처리
    @PostMapping("/portfolio_modify")
    public String modify(
            PortfolioEntity form,
            @RequestParam(value="file", required = false) MultipartFile file,
            @RequestParam(value="page", defaultValue="1") int page) throws IOException {

        PortfolioEntity entity = portfolioService.getView(form.getIdx());

        entity.setTitle(form.getTitle());
        entity.setContent(form.getContent());

        // 파일 업로드 처리
        if (file != null && !file.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String ext = file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf("."));
            String saveName = uuid + ext;

            File saveFile = new File(uploadPath, saveName);
            file.transferTo(saveFile);

            entity.setFilename(saveName);
        }

        portfolioService.modify(entity);

        return "redirect:/portfolio/portfolio_view?idx=" + entity.getIdx() + "&page=" + page;
    }

    // 삭제
    @PostMapping("/portfolio_delete")
    public String delete(@RequestParam("idx") int idx) {
        portfolioService.delete(idx);
        return "redirect:/portfolio/portfolio_list";
    }
}