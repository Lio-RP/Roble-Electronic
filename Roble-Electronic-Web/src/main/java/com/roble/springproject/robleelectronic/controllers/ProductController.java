package com.roble.springproject.robleelectronic.controllers;

import com.roble.springproject.robleelectronic.models.Category;
import com.roble.springproject.robleelectronic.models.Product;
import com.roble.springproject.robleelectronic.services.CategoryService;
import com.roble.springproject.robleelectronic.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @ModelAttribute("categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("categories")
    public String getCategories(){

        return "fragments/layout";
    }

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("listProducts", productService.getAllProducts());
        return "product/index";
    }

    @GetMapping("category/{categoryId}/products")
    public String getProductsByCategory(@PathVariable("categoryId") Long categoryId,
                                        Model model){
        List<Product> listProducts = productService.getProductsBasedOnCategory(categoryId);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("category", categoryService.getCategoryById(categoryId));

        return "product/listProducts";
    }

    @GetMapping("products/{productId}/view")
    public String productView(@PathVariable("productId") Long productId, Model model){
        model.addAttribute("product", productService.getProductById(productId));
        return "product/productDetails";
    }
}
