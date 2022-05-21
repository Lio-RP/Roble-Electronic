package com.roble.springproject.robleelectronic.services;

import com.roble.springproject.robleelectronic.models.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {

    Product save(Product object, Long categoryId);

    List<Product> getAllProducts();

    List<Product> getProductsBasedOnCategory(Long id);

    Product getProductById(Long productId);

    List<Product> findByNameLike(String name);

    List<Product> findByNameLike(String name, String description);

    void deleteById(Long productId);
}
