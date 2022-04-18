package com.roble.springproject.robleelectronic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping({"","/","/index","index.html"})
    public String index(){
        return "index";
    }
}
