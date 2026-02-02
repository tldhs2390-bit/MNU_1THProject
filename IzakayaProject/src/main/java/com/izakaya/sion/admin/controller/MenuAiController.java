package com.izakaya.sion.admin.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.izakaya.sion.domain.MenuAiRequest;
import com.izakaya.sion.service.MenuAiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/menu")
@RequiredArgsConstructor
public class MenuAiController {

    private final MenuAiService menuAiService;

    @PostMapping("/ai-generate")
    public Map<String, String> generate(@RequestBody MenuAiRequest req) {
        try {
            Map<String, String> result = menuAiService.generate(req);

            // ✅ 혹시라도 {}가 오면 프론트가 멈춘 느낌이라 최소값 반환
            if (result == null || result.isEmpty()) {
                return Map.of(
                        "category", "SINGLE",
                        "description", "香ばしく仕上げた一品です。食感と香りのバランスがよく、お酒のお供にもよく合います。"
                );
            }

            return result;

        } catch (Exception e) {
            // ✅ 429/네트워크 등 어떤 예외든 프론트에서 처리 가능하게 fallback 반환
            return Map.of(
                    "category", "SINGLE",
                    "description", "ただいまAIが混み合っています。少し時間をおいて再度お試しください。"
            );
        }
    }
}