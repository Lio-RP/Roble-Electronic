package com.roble.springproject.robleelectronic.controllers;

import com.roble.springproject.robleelectronic.models.Category;
import com.roble.springproject.robleelectronic.models.Product;
import com.roble.springproject.robleelectronic.services.CategoryService;
import com.roble.springproject.robleelectronic.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/roble_elco/admin/")
public class AdminController {

    public final CategoryService categoryService;
    public final ProductService productService;

    public AdminController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @ModelAttribute("categories")
    public Set<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("categories")
    public String getCategories(Model model){
        return "admin/listCategories";
    }

//    @GetMapping("products")
//    public String getProducts(Model model){
//        model.addAttribute("listProducts", productService.getAllProducts());
//        return "admin/listProducts";
//    }

    @GetMapping("categories/{categoryId}/products")
    public String getProductsBasedOnCategory(@PathVariable("categoryId") Long categoryId,
                                             Model model){
        model.addAttribute("category", categoryService.getCategoryById(categoryId));
        model.addAttribute("listProducts",
                productService.getProductsBasedOnCategory(categoryId));
        return "admin/listProducts";
    }

    @GetMapping("categories/{categoryId}/delete")
    public String deleteCategory(@PathVariable("categoryId") Long categoryId){
        categoryService.deleteById(categoryId);
        return "redirect:/roble_elco/admin/categories";
    }

    @GetMapping("categories/{categoryId}/products/{productId}/delete")
    public String deleteProduct(@PathVariable("categoryId") Long categoryId,
                                @PathVariable("productId") Long productId){

        productService.deleteById(productId);

        return "redirect:/roble_elco/admin/categories/" + categoryId + "/products";
    }

    @GetMapping({"products/{productId}/view"})
    public String viewProducts(@PathVariable("productId") Long productId
            ,Model model){

        model.addAttribute("product", productService.getProductById(productId));
        return "admin/productDetails";
    }

    @GetMapping("categories/{categoryId}/products/new")
    public String initCreateProductForm(@PathVariable("categoryId") Long categoryId,
                                    Model model){
        //model.addAttribute("category", categoryService.getCategoryById(categoryId));
        model.addAttribute("product", new Product());
        return "admin/createOrUpdateProduct";
    }

    @PostMapping("categories/{categoryId}/products/new")
    public String processCreatingProduct(@ModelAttribute("product") Product product,
                                         @PathVariable("categoryId") Long categoryId){
        Category category = categoryService.getCategoryById(categoryId);
        product.setCategory(category);
        Product savedProduct = productService.save(product, categoryId);

        return "redirect:/roble_elco/admin/products/" + savedProduct.getId() + "/view";
    }

    @GetMapping("categories/{categoryId}/products/{productId}/edit")
    public String initUpdateProductForm(@PathVariable("categoryId") Long categoryId,
                                        @PathVariable("productId") Long productId,
                                        Model model){
        Product product = productService.getProductById(productId);
        //Category category = categoryService.getCategoryById(categoryId);
        //model.addAttribute("category", category);
        model.addAttribute("product",product);
        return "admin/createOrUpdateProduct";
    }

    @PostMapping("categories/{categoryId}/products/{productId}/edit")
    public String processUpdatingProduct(@ModelAttribute("product") Product product,
                                         @PathVariable("categoryId") Long categoryId,
                                         @PathVariable("productId") Long productId){

        Category category = categoryService.getCategoryById(categoryId);
        product.setId(productId);
        product.setCategory(category);
        Product savedProduct = productService.save(product, categoryId);
        return "redirect:/roble_elco/admin/products/" + savedProduct.getId() + "/view";
    }
}
