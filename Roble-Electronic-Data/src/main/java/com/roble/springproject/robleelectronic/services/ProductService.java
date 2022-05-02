package com.roble.springproject.robleelectronic.services;

import com.roble.springproject.robleelectronic.models.Product;

import java.util.Set;

public interface ProductService {

    Product save(Product object, Long categoryId);

    Set<Product> getAllProducts();

    Set<Product> getProductsBasedOnCategory(Long id);

    Product getProductById(Long productId);

    void deleteById(Long productId);
}
