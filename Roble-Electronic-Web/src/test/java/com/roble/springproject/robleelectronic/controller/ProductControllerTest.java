package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.models.Category;
import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.services.CategoryService;
import com.roble.springproject.RobleElectronic.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    @Mock
    CategoryService categoryService;

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

       mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void getCategories() throws Exception {

        mockMvc.perform(get("/roble_elco/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments/layout"));
    }

    @Test
    public void home() throws Exception {

        List<Product> products = Arrays.asList(new Product(), new Product(), new Product());

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/roble_elco"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/index"))
                .andExpect(model().attribute("listProducts", hasSize(3)));

    }

    @Test
    public void getProductsByCategory() throws Exception {

        List<Product> products = Arrays.asList(new Product(), new Product(), new Product());

        when(productService.getProductsBasedOnCategory(anyLong())).thenReturn(products);
        when(categoryService.getCategoryById(anyLong())).thenReturn(new Category());

        mockMvc.perform(get("/roble_elco/category/1/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/listProducts"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attribute("listProducts", hasSize(3)));
    }

    @Test
    public void productView() throws Exception {

        when(productService.getProductById(anyLong())).thenReturn(new Product());

        mockMvc.perform(get("/roble_elco/products/1/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productDetails"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void proccessFindProduct() throws Exception {

        List<Product> productList = Arrays.asList(new Product(), new Product(), new Product());

        when(productService.findByNameLike(anyString(), anyString())).thenReturn(productList);
        when(categoryService.findByDescription(anyString())).thenReturn(new Category());

        mockMvc.perform(get("/roble_elco/products")
                .param("name", "product NAME")
                        .param("category.description", "product description"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/listProducts"))
                .andExpect(model().attribute("listProducts", hasSize(3)))
                .andExpect(model().attributeExists("category"));

        verify(productService).findByNameLike(anyString(), anyString());
    }
}