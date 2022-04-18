package com.roble.springproject.robleelectronic.repositories;

import com.roble.springproject.robleelectronic.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
