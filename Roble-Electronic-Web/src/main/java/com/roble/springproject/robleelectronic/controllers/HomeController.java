package com.roble.springproject.robleelectronic.controllers;

import com.roble.springproject.robleelectronic.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping({"","/","/index","index.html"})
    public String index(Model model){

        return "index";
    }
}
