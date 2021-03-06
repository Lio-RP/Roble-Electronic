package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.models.Category;
import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.services.CategoryService;
import com.roble.springproject.RobleElectronic.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/roble_elco/admin/")
public class AdminController {

    public static final String CREATE_OR_UPDATE_PRODUCT = "admin/createOrUpdateProduct";
    public final CategoryService categoryService;
    public final ProductService productService;

    public AdminController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @ModelAttribute("product")
    public Product productObject(){
        return new Product();
    }

    @ModelAttribute("categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("categories")
    public String getCategories(Model model){
        model.addAttribute("categories", categoryService.getAllCategories());
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

        productService.deleteById(productId, categoryId);

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
        return CREATE_OR_UPDATE_PRODUCT;
    }

    @PostMapping("categories/{categoryId}/products/new")
    public String processCreatingProduct(@Valid @ModelAttribute("product") Product product,
                                         BindingResult result,
                                         @PathVariable("categoryId") Long categoryId){
        if(result.hasErrors()){
            return CREATE_OR_UPDATE_PRODUCT;
        }else {
            Category category = categoryService.getCategoryById(categoryId);
            product.setCategory(category);
            Product savedProduct = productService.save(product, categoryId);

            return "redirect:/roble_elco/admin/products/" + savedProduct.getId() + "/view";
        }

    }

    @GetMapping("categories/{categoryId}/products/{productId}/edit")
    public String initUpdateProductForm(@PathVariable("categoryId") Long categoryId,
                                        @PathVariable("productId") Long productId,
                                        Model model){
        Product product = productService.getProductById(productId);
        //Category category = categoryService.getCategoryById(categoryId);
        //model.addAttribute("category", category);
        model.addAttribute("product",product);
        return CREATE_OR_UPDATE_PRODUCT;
    }

    @PostMapping("categories/{categoryId}/products/{productId}/edit")
    public String processUpdatingProduct(@Valid @ModelAttribute("product") Product product,
                                         BindingResult result,
                                         @PathVariable("categoryId") Long categoryId,
                                         @PathVariable("productId") Long productId){
        if(result.hasErrors()){
            return CREATE_OR_UPDATE_PRODUCT;
        }else{
            Category category = categoryService.getCategoryById(categoryId);
            product.setId(productId);
            product.setCategory(category);
            Product savedProduct = productService.save(product, categoryId);
            return "redirect:/roble_elco/admin/products/" + savedProduct.getId() + "/view";
        }
    }
}
