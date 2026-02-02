package com.izakaya.sion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // 인트로 화면
    @GetMapping("/intro")
    public String intro() {
        return "intro";
    }

    // 메인 인덱스 (이자카야 내부)
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
