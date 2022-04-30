package com.roble.springproject.robleelectronic.repositories;

import com.roble.springproject.robleelectronic.models.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
}
