package com.roble.springproject.RobleElectronic.services;

import com.roble.springproject.RobleElectronic.models.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart addToCart(Long productId);
}
