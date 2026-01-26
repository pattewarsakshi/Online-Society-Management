package com.society.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping({
            "/login",
            "/guard",
            "/guard/**",
            "/admin",
            "/admin/**",
            "/resident",
            "/resident/**"
    })
    public String forwardToIndex() {
        return "forward:/index.html";
    }
}
