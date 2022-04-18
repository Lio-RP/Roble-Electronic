package com.roble.springproject.robleelectronic.services.imple;

import com.roble.springproject.robleelectronic.models.Product;
import com.roble.springproject.robleelectronic.repositories.ProductRepository;
import com.roble.springproject.robleelectronic.services.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product object) {
        return productRepository.save(object);
    }
}
