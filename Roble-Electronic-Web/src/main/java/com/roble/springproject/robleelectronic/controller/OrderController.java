package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.models.Category;
import com.roble.springproject.RobleElectronic.models.Customer;
import com.roble.springproject.RobleElectronic.models.Payment;
import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.services.CategoryService;
import com.roble.springproject.RobleElectronic.services.ProductService;
import com.roble.springproject.RobleElectronic.services.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/roble_elco/")
@Controller
public class OrderController {

    public final ProductService productService;
    public final ShoppingCartService shoppingCartService;
    public final CategoryService categoryService;

    public OrderController(ProductService productService,
                           ShoppingCartService shoppingCartService,
                           CategoryService categoryService) {
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("product")
    public Product productObject(){
        return new Product();
    }

    @ModelAttribute("categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("checkout")
    public String initCheckoutForm(Model model){

        model.addAttribute("customer", new Customer());
        return "order/checkout";
    }

    @PostMapping("checkout")
    public String processCheckoutForm(@ModelAttribute("customer") Customer customer,
                                      BindingResult result,
                                      Model model){
        if(result.hasErrors()){
            return "order/checkout";
        }
        return "redirect:/roble_elco/payment";

    }

    @GetMapping("payment")
    public String initPaymentForm(Model model){

        model.addAttribute("payment", new Payment());
        return "order/payment";
    }
}
