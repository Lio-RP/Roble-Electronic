package com.roble.springproject.robleelectronic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/roble_elco/home")
    public String home(Model model){

        return "product/home";
    }
}
