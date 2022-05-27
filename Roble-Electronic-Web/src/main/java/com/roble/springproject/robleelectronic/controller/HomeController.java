package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.models.Category;
import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    public final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ModelAttribute("categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @ModelAttribute("product")
    public Product productObject(){
        return new Product();
    }

    @RequestMapping({"","/","/index","index.html"})
    public String index(Model model){

        return "index";
    }
}
