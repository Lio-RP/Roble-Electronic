package com.roble.springproject.robleelectronic.controllers;

import com.roble.springproject.robleelectronic.models.Customer;
import com.roble.springproject.robleelectronic.models.Payment;
import com.roble.springproject.robleelectronic.services.ProductService;
import com.roble.springproject.robleelectronic.services.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/roble_elco/")
@Controller
public class OrderController {

    public final ProductService productService;
    public final ShoppingCartService shoppingCartService;

    public OrderController(ProductService productService,
                           ShoppingCartService shoppingCartService) {
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("home/products/{productId}/addCart")
    public String addToCart(@PathVariable("productId") Long productId,
                            Model model){

        return "";
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
