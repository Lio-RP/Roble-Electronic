package com.roble.springproject.robleelectronic.controllers;

import com.roble.springproject.robleelectronic.models.Category;
import com.roble.springproject.robleelectronic.models.Product;
import com.roble.springproject.robleelectronic.services.CategoryService;
import com.roble.springproject.robleelectronic.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/roble_elco")
@Controller
public class ProductController {

    public final CategoryService categoryService;
    public final ProductService productService;

    public ProductController(CategoryService categoryService,
                             ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("listProducts", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "product/index";
    }

    @GetMapping("category/{categoryId}/products")
    public String getProductsByCategory(@PathVariable("categoryId") Long categoryId,
                                        Model model){
        Set<Product> listProducts = productService.getProductsBasedOnCategory(categoryId);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("category", categoryService.getCategoryById(categoryId));

        return "product/listProducts";
    }
}
