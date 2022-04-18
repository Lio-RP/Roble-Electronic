package com.roble.springproject.robleelectronic.repositories;

import com.roble.springproject.robleelectronic.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
