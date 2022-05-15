package com.roble.springproject.robleelectronic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}