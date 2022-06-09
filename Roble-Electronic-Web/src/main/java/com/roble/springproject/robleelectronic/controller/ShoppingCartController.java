package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.auth.UserPrinciples;
import com.roble.springproject.RobleElectronic.models.Category;
import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.models.ShoppingCart;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.services.CategoryService;
import com.roble.springproject.RobleElectronic.services.ProductService;
import com.roble.springproject.RobleElectronic.services.ShoppingCartService;
import com.roble.springproject.RobleElectronic.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/roble_elco/")
@Controller
public class ShoppingCartController {

    public final ProductService productService;
    public final ShoppingCartService shoppingCartService;
    public final UserService userService;
    public final CategoryService categoryService;

    public ShoppingCartController(ProductService productService,
                                  ShoppingCartService shoppingCartService,
                                  UserService userService,
                                  CategoryService categoryService) {
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
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

    @GetMapping("products/{productId}/addToCart")
    public String addToCart(@PathVariable("productId") Long productId,
                            Model model,
                            @AuthenticationPrincipal UserPrinciples userPrincipal){
        User user = userService.getCurrentlyLoggedUser(userPrincipal);
        shoppingCartService.addToCart(productId, user);

        return "redirect:/roble_elco/cart";
    }

    @GetMapping("cart")
    public String displayCart(Model model,
                              @AuthenticationPrincipal UserPrinciples userPrincipal){

        User user = userService.getCurrentlyLoggedUser(userPrincipal);
        List<ShoppingCart> listCarts = shoppingCartService.listCartsByUser(user);
        float totalCartsPrice = shoppingCartService.calTotalCartsPrice(listCarts);
        int totalCartsQuantity = shoppingCartService.calTotalCartsQuantity(listCarts);
        model.addAttribute("totalCartsPrice", totalCartsPrice);
        model.addAttribute("totalCartsQuantity", totalCartsQuantity);
        model.addAttribute("listCarts", listCarts);

        return "cart/shoppingCart";
    }

    @GetMapping("cart/{cartId}/deleteItem")
    public String deleteCartItem(@PathVariable("cartId") Long cartId,
                                 @AuthenticationPrincipal UserPrinciples userPrincipal){
        User user = userService.getCurrentlyLoggedUser(userPrincipal);
        shoppingCartService.deleteCartItem(cartId, user);
        System.out.println();
        return "redirect:/roble_elco/cart";
    }

    @GetMapping("cart/{cartId}/update")
    public String updateCartItem(@PathVariable("cartId") Long cartId,
                                 @AuthenticationPrincipal UserPrinciples userPrincipal){
        User user = userService.getCurrentlyLoggedUser(userPrincipal);
        shoppingCartService.updateCartItem(cartId, user);
        return "redirect:/roble_elco/cart";
    }

    @GetMapping("cart/{cartId}/decrease")
    public String decreaseCartItem(@PathVariable("cartId") Long cartId,
                                   @AuthenticationPrincipal UserPrinciples userPrincipal){
        User user = userService.getCurrentlyLoggedUser(userPrincipal);
        shoppingCartService.decreaseCartItem(cartId, user);
        return "redirect:/roble_elco/cart";
    }


}
