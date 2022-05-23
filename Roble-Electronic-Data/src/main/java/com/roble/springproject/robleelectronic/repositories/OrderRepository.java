package com.roble.springproject.RobleElectronic.repositories;

import com.roble.springproject.RobleElectronic.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
