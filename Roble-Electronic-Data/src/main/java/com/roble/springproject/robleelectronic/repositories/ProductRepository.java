package com.roble.springproject.robleelectronic.repositories;

import com.roble.springproject.robleelectronic.models.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT product FROM Product product WHERE LOWER( product.name) Like %:name%")
    @Transactional(readOnly = true)
    List<Product> findByNameLike(@Param("name") String name);

    @Query("SELECT product FROM Product product WHERE LOWER(product.name)" +
            "Like %:name% AND LOWER(product.category.description) = LOWER(:description)")
    @Transactional(readOnly = true)
    List<Product> findByNameLike(@Param("name") String name, @Param("description") String description);
}
