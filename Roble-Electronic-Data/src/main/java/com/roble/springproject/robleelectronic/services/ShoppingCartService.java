package com.roble.springproject.RobleElectronic.services;

import com.roble.springproject.RobleElectronic.models.ShoppingCart;
import com.roble.springproject.RobleElectronic.models.User;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCart addToCart(Long productId, User user);

    List<ShoppingCart> listCartsByUser(User user);

    float calTotalCartsPrice(List<ShoppingCart> listCarts);

    int calTotalCartsQuantity(List<ShoppingCart> listCarts);

    void deleteCartItem(Long cartId, User user);

    void updateCartItem(Long cartId, User user);

    void decreaseCartItem(Long cartId, User user);
}
