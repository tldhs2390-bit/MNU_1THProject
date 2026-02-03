package com.izakaya.sion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CampaignController {

    @GetMapping("/campaign")
    public String campaign() {
        return "campaign";
    }
}
