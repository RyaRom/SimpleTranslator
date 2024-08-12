package com.TranslationApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/mainpage")
    public String getMainPage() {
        return "mainpage";
    }
}
