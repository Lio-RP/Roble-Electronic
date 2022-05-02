package com.roble.springproject.robleelectronic.services.imple;

import com.roble.springproject.robleelectronic.models.Category;
import com.roble.springproject.robleelectronic.models.Product;
import com.roble.springproject.robleelectronic.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void save() {
        //Given
        Category cat = new Category();
        cat.setId(2L);
        cat.setDescription("Some Description");

        Product prod = new Product();
        prod.setId(1L);
        prod.setName("Product Name");
        prod.setDescription("Product Description.");
        prod.setPrice(new BigDecimal(3999));

        prod.setCategory(cat);
        cat.getProducts().add(prod);

        //When
        when(productRepository.save(any())).thenReturn(prod);

        Product savedProd = productService.save(prod, cat.getId());

        //Then
        verify(productRepository).save(any());
        assertNotNull(savedProd);
        assertEquals(1L, savedProd.getId());
        assertEquals(2L, savedProd.getCategory().getId());
    }
}