package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.auth.UserPrinciples;
import com.roble.springproject.RobleElectronic.models.*;
import com.roble.springproject.RobleElectronic.services.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/roble_elco/")
@Controller
public class OrderController {

    public final ProductService productService;
    public final ShoppingCartService shoppingCartService;
    public final CategoryService categoryService;
    public final UserService userService;
    private final CustomerService customerService;
    private final OrderService orderService;

    public OrderController(ProductService productService,
                           ShoppingCartService shoppingCartService,
                           CategoryService categoryService,
                           UserService userService,
                           CustomerService customerService,
                           OrderService orderService) {
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @ModelAttribute("product")
    public Product productObject(){
        return new Product();
    }

    @ModelAttribute("categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    public List<ShoppingCart> getListCarts(UserPrinciples userPrincipal){
        User user = userService.getCurrentlyLoggedUser(userPrincipal);

        return shoppingCartService.listCartsByUser(user);
    }

    public float getTotalCartsPrice(List<ShoppingCart> lists){
        return shoppingCartService.calTotalCartsPrice(lists);
    }

    public int getTotalCartsQuantity(List<ShoppingCart> lists){
        return shoppingCartService.calTotalCartsQuantity(lists);
    }

    @GetMapping("checkout")
    public String initCheckoutForm(Model model,
                                   @AuthenticationPrincipal UserPrinciples userPrincipal){

        model.addAttribute("totalCartsPrice", getTotalCartsPrice(getListCarts(userPrincipal)));
        model.addAttribute("totalCartsQuantity", getTotalCartsQuantity(getListCarts(userPrincipal)));
        model.addAttribute("listCarts", getListCarts(userPrincipal));
        model.addAttribute("customer", new Customer());
        return "order/checkout";
    }

    @PostMapping("checkout")
    public String processCheckoutForm(@Valid  @ModelAttribute("customer") Customer customer,
                                      BindingResult result,
                                      Model model,
                                      @AuthenticationPrincipal UserPrinciples userPrincipal){
        if(result.hasErrors()){
            model.addAttribute("totalCartsPrice", getTotalCartsPrice(getListCarts(userPrincipal)));
            model.addAttribute("totalCartsQuantity", getTotalCartsQuantity(getListCarts(userPrincipal)));
            model.addAttribute("listCarts", getListCarts(userPrincipal));
            return "order/checkout";
        }

        User user = userService.getCurrentlyLoggedUser(userPrincipal);
        customerService.save(customer, user);

        return "redirect:/roble_elco/payment";

    }

    @GetMapping("payment")
    public String initPaymentForm(Model model,
                                  @AuthenticationPrincipal UserPrinciples userPrincipal){

        model.addAttribute("totalCartsPrice", getTotalCartsPrice(getListCarts(userPrincipal)));
        model.addAttribute("totalCartsQuantity", getTotalCartsQuantity(getListCarts(userPrincipal)));
        model.addAttribute("listCarts", getListCarts(userPrincipal));
        model.addAttribute("payment", new Payment());
        return "order/payment";
    }

    @PostMapping("payment")
    public String processPaymentForm(@Valid  @ModelAttribute("payment") Payment payment,
                                      BindingResult result,
                                     @AuthenticationPrincipal UserPrinciples userPrincipal){
        if(result.hasErrors()){
            return "order/payment";
        }
        User user = userService.getCurrentlyLoggedUser(userPrincipal);
        orderService.save(user);
        return "redirect:/roble_elco/receipt";

    }

    @GetMapping("/receipt")
    public String receipt(Model model,
                          @AuthenticationPrincipal UserPrinciples userPrincipal){

        model.addAttribute("totalCartsPrice", getTotalCartsPrice(getListCarts(userPrincipal)));
        model.addAttribute("totalCartsQuantity", getTotalCartsQuantity(getListCarts(userPrincipal)));
        model.addAttribute("listCarts", getListCarts(userPrincipal));

        User user = userService.getCurrentlyLoggedUser(userPrincipal);
        shoppingCartService.clearCart(user);
        return "order/receipt";
    }
}
