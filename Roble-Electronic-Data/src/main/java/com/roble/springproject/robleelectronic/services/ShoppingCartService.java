package com.roble.springproject.robleelectronic.services;

import com.roble.springproject.robleelectronic.models.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart addToCart(Long productId);
}
