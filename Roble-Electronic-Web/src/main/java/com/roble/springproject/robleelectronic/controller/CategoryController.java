package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.services.CategoryService;
import org.springframework.stereotype.Controller;

@Controller
public class CategoryController {

    public final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

}
