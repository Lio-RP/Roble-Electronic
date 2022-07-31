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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminControllerTest {

    @Mock
    CategoryService categoryService;

    @Mock
    ProductService productService;

    @InjectMocks
    AdminController adminController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void getCategories() throws Exception {

        Category category1 = new Category();
        category1.setId(1L);
        category1.setDescription("Category1 Descriptions.");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setDescription("Category1 Descriptions.");

        List<Category> categoryList = Arrays.asList(category1, category2);

        when(categoryService.getAllCategories()).thenReturn(categoryList);

        mockMvc.perform(get("/roble_elco/admin/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/listCategories"))
                .andExpect(model().attribute("categories", hasSize(2)));

    }

    @Test
    public void getProductsBasedOnCategory() throws Exception {

        Product prod1 = new Product();
        prod1.setId(1L);
        prod1.setName("Iphone");
        prod1.setBrand("Apple");
        prod1.setPrice(123);
        prod1.setDescription("Product1 description.");


        Product prod2 = new Product();
        prod2.setId(2L);
        prod2.setName("Samsung");
        prod2.setBrand("Samsung");
        prod2.setPrice(123);
        prod2.setDescription("Product2 description.");

        Category category = new Category();
        category.setId(1L);
        category.setDescription("Category1 Descriptions.");

        category.getProducts().add(prod1);
        category.getProducts().add(prod2);

        when(categoryService.getCategoryById(anyLong())).thenReturn(category);
        when(productService.getProductsBasedOnCategory(anyLong())).thenReturn(category.getProducts());

        mockMvc.perform(get("/roble_elco/admin/categories/1/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/listProducts"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attribute("listProducts", hasSize(2)));


    }

    @Test
    public void deleteCategoryById() throws Exception {

        mockMvc.perform(get("/roble_elco/admin/categories/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/roble_elco/admin/categories"));

        verify(categoryService).deleteById(anyLong());
    }

    @Test
    public void deleteProduct() throws Exception {

        Category category = new Category();
        category.setId(1L);
        category.setDescription("Category Description.");

        Long categoryId = category.getId();

        mockMvc.perform(get("/roble_elco/admin/categories/1/products/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/roble_elco/admin/categories/" + categoryId + "/products"));

        verify(productService).deleteById(anyLong(), anyLong());
    }

    @Test
    public void viewProducts() throws Exception {

        Product prod1 = new Product();
        prod1.setId(1L);
        prod1.setName("Iphone");
        prod1.setBrand("Apple");
        prod1.setPrice(123);
        prod1.setDescription("Product1 description.");

        when(productService.getProductById(anyLong())).thenReturn(prod1);

        mockMvc.perform(get("/roble_elco/admin/products/1/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/productDetails"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void initCreateProductForm() throws Exception {

        mockMvc.perform(get("/roble_elco/admin/categories/1/products/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void processCreatingProduct() throws Exception {
        Product prod1 = new Product();
        prod1.setId(1L);
        prod1.setName("Iphone");
        prod1.setBrand("Apple");
        prod1.setPrice(123);
        prod1.setDescription("Product1 description.");

        when(productService.save(any(), anyLong())).thenReturn(prod1);

        mockMvc.perform(post("/roble_elco/admin/categories/1/products/new")
                        .param("id", "1")
                        .param("name", "Iphone")
                        .param("description", "Product Description")
                        .param("brand", "Apple")
                        .param("price", "1234")
                        .param("inStock", "12"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/roble_elco/admin/products/" + prod1.getId() + "/view"));
    }

    @Test
    public void processCreatingProductFailedFormValidation() throws Exception {
        Product prod1 = new Product();
        prod1.setId(1L);
        prod1.setName("Iphone");
        prod1.setBrand("Apple");
        prod1.setPrice(123);
        prod1.setDescription("Product1 description.");

        when(productService.save(any(), anyLong())).thenReturn(prod1);

        mockMvc.perform(post("/roble_elco/admin/categories/1/products/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createOrUpdateProduct"));
    }

    @Test
    public void initUpdateProductForm() throws Exception {

        Product prod1 = new Product();
        prod1.setId(1L);
        prod1.setName("Iphone");
        prod1.setBrand("Apple");
        prod1.setPrice(123);
        prod1.setDescription("Product1 description.");

        when(productService.getProductById(anyLong())).thenReturn(prod1);

        mockMvc.perform(get("/roble_elco/admin/categories/1/products/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void processUpdatingProductFailedFormValidation() throws Exception {
        Product prod1 = new Product();
        prod1.setId(1L);
        prod1.setName("Iphone");
        prod1.setBrand("Apple");
        prod1.setPrice(123);
        prod1.setDescription("Product1 description.");

        when(productService.save(any(), anyLong())).thenReturn(prod1);

        mockMvc.perform(post("/roble_elco/admin/categories/1/products/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createOrUpdateProduct"));
    }

    @Test
    public void processUpdatingProduct() throws Exception {
        Product prod1 = new Product();
        prod1.setId(1L);
        prod1.setName("Iphone");
        prod1.setBrand("Apple");
        prod1.setPrice(123);
        prod1.setDescription("Product1 description.");

        when(productService.save(any(), anyLong())).thenReturn(prod1);

        mockMvc.perform(post("/roble_elco/admin/categories/1/products/1/edit")
                        .param("id", "1")
                        .param("name", "Iphone")
                        .param("description", "Product Description")
                        .param("brand", "Apple")
                        .param("price", "1234")
                        .param("inStock", "12"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/roble_elco/admin/products/" + prod1.getId() + "/view"));
    }
}