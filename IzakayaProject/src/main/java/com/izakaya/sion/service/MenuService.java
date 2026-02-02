package com.izakaya.sion.service;

import com.izakaya.sion.domain.Menu;
import com.izakaya.sion.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;

    // ✅ application.properties에서 주입 (없으면 기본값)
    @Value("${app.upload.menu-dir:uploads/menu}")
    private String menuUploadDir; // 예: uploads/menu (프로젝트 루트 기준)

    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    @Transactional(readOnly = true)
    public List<Menu> findAllForAdmin() {
        return menuRepository.findAllByOrderBySortOrderAscIdDesc();
    }

    @Transactional(readOnly = true)
    public Menu findById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found: " + id));
    }

    public void delete(Long id) {
        menuRepository.deleteById(id);
    }

    // ✅✅✅ 업로드 파일 저장 후, DB에 넣을 경로(/uploads/menu/xxx) 리턴
    public String saveMenuImage(MultipartFile file) {
        try {
            String original = file.getOriginalFilename() != null ? file.getOriginalFilename() : "";
            String ext = "";

            int dot = original.lastIndexOf('.');
            if (dot >= 0) ext = original.substring(dot);

            String filename = UUID.randomUUID() + ext;

            // 실제 저장 폴더 (프로젝트 실행 위치 기준)
            Path dir = Paths.get(menuUploadDir);
            Files.createDirectories(dir);

            Path target = dir.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            // 브라우저 접근 경로로 저장
            return "/uploads/menu/" + filename;

        } catch (IOException e) {
            throw new IllegalStateException("メニュー画像の保存に失敗しました。", e);
        }
    }
}