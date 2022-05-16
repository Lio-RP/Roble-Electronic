package com.roble.springproject.robleelectronic.controllers;

import com.roble.springproject.robleelectronic.models.Category;
import com.roble.springproject.robleelectronic.models.Customer;
import com.roble.springproject.robleelectronic.models.Payment;
import com.roble.springproject.robleelectronic.services.CategoryService;
import com.roble.springproject.robleelectronic.services.ProductService;
import com.roble.springproject.robleelectronic.services.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    @ModelAttribute("categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("products/{productId}/addToCart")
    public String addToCart(@PathVariable("productId") Long productId,
                            Model model){
        shoppingCartService.addToCart(productId);
        Category category = productService.getProductById(productId).getCategory();
        return "redirect:/roble_elco/category/" + category.getId() + "/products";
    }

    @GetMapping("cart")
    public String displayCart(Model model){

        return "order/cart";
    }

    @GetMapping("checkout")
    public String initCheckoutForm(Model model){

        //model.addAttribute("orderList", shoppingCartService.getAllOrders());
        model.addAttribute("customer", new Customer());
        return "order/checkout";
    }

    @PostMapping("checkout")
    public String processCheckoutForm(@ModelAttribute("customer") Customer customer,
                                      Model model){

        return "redirect:/roble_elco/payment";

    }

    @GetMapping("payment")
    public String initPaymentForm(Model model){

        model.addAttribute("payment", new Payment());
        return "order/payment";
    }
}
