package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.models.Category;
import com.roble.springproject.RobleElectronic.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    CategoryServiceImpl categoryService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    public void save() {
        Category category = new Category();
        category.setId(1L);
        category.setDescription("Category Description");

        when(categoryRepository.save(any())).thenReturn(category);

        //when
        Category savedCategory = categoryService.save(category);

        //then
        assertNotNull(savedCategory);
        assertEquals(Long.valueOf(1L), savedCategory.getId());
    }

    @Test
    public void getAllCategories() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setDescription("Category Description");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setDescription("Category Description");

        List<Category> categoryList = Arrays.asList(category1, category2);

        when(categoryRepository.findAll()).thenReturn(categoryList);

        //when
        List<Category> categories = categoryService.getAllCategories();

        //then
        assertNotNull(categories);
        assertEquals(2, categories.size());
    }

    @Test
    public void getCategoryById() {

        Category category = new Category();
        category.setId(1L);
        category.setDescription("Category Description");

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        //when
        Category foundCategory = categoryService.getCategoryById(1L);

        //then
        assertNotNull(foundCategory);
        assertEquals(Long.valueOf(1L), foundCategory.getId());
        assertEquals("Category Description", foundCategory.getDescription());
    }

    @Test
    public void findByDescription() {

        Category category = new Category();
        category.setId(1L);
        category.setDescription("Category Description");

        when(categoryRepository.findByDescription(anyString())).thenReturn(category);

        //when
        Category foundCategory = categoryService.findByDescription(category.getDescription());

        //then
        assertNotNull(foundCategory);
        assertEquals(Long.valueOf(1L), foundCategory.getId());
        assertEquals("Category Description", foundCategory.getDescription());
    }

    @Test
    public void deleteAll() {

        categoryService.deleteAll();

        verify(categoryRepository, times(1)).deleteAll();
    }
}