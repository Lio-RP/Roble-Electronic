package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.models.Category;
import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.services.CategoryService;
import com.roble.springproject.RobleElectronic.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @ModelAttribute("product")
    public Product productObject(){
        return new Product();
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

    @GetMapping("/products")
    public String proccessFindProduct(Product product,
                                      BindingResult result,
                                      Model model){

        List<Product> listProducts = new ArrayList<>();
        String name = "";
        String description = "";

        if(product.getCategory().getDescription() == ""){
            System.out.println("the description is empty");
            name = product.getName() == null ?  "": product.getName();
            listProducts = productService.findByNameLike(name.toLowerCase());
        }else {
            System.out.println("the description is not empty");
            name = product.getName();
            description = product.getCategory().getDescription();
            listProducts = productService.findByNameLike(name.toLowerCase(), description);
            model.addAttribute("category", categoryService.findByDescription(description));
        }

        System.out.println(listProducts);
        System.out.println(categoryService.findByDescription(description));

        if(listProducts.isEmpty()){

            //Not found document;
            result.rejectValue("name", "notFound", "Not Found");
            return "product/index";
        }else if (listProducts.size() == 1) {

            //found one product:
            return "redirect:/roble_elco/products/" + listProducts.get(0).getId() + "/view";

        }else {

            //found more then one
            model.addAttribute("listProducts", listProducts);
            return "product/listProducts";
        }
    }
}
