package com.roble.springproject.RobleElectronic.repositories;

import com.roble.springproject.RobleElectronic.models.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
}
