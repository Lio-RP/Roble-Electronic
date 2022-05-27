package com.roble.springproject.RobleElectronic.repositories;

import com.roble.springproject.RobleElectronic.models.ShoppingCart;
import com.roble.springproject.RobleElectronic.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

    @Query("SELECT cart FROM ShoppingCart cart WHERE cart.user = :user")
    List<ShoppingCart> findByUser(@Param("user") User user);
}
