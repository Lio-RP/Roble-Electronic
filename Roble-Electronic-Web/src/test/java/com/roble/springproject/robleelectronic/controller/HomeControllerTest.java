package com.roble.springproject.RobleElectronic.controller;


import com.roble.springproject.RobleElectronic.models.Category;
import com.roble.springproject.RobleElectronic.services.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class HomeControllerTest {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    HomeController homeController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void getAllCategories() throws Exception {
        Category cat1 = new Category();
        cat1.setId(1L);
        cat1.setDescription("Category Description");

        Category cat2 = new Category();
        cat2.setId(2L);
        cat2.setDescription("Category Description");

        List<Category> categories = Arrays.asList(cat1, cat2);

        when(categoryService.getAllCategories()).thenReturn(categories);

        //when
        List<Category> categoryList = homeController.getAllCategories();

        //Then
        assertNotNull(categoryList);
        assertEquals(2, categoryList.size());
    }

    @Test
    public void index() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}