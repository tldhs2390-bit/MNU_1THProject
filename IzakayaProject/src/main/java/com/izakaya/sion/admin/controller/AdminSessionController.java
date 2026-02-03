package com.izakaya.sion.admin.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminSessionController {

    @GetMapping("/session-check")
    public boolean sessionCheck(Authentication authentication) {
        if (authentication == null) return false;
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        return authentication.isAuthenticated();
    }

    // ✅ 여기만 수정: "/admin/session-keepalive" 로 맞춤
    @GetMapping("/session-keepalive")
    public void keepAlive(Authentication authentication) {
        if (authentication == null) return;
        if (authentication instanceof AnonymousAuthenticationToken) return;
        // 요청 들어오면 세션 갱신됨
    }
}