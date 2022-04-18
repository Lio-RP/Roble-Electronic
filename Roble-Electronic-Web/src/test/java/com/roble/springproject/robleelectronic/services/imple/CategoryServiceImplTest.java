package com.roble.springproject.robleelectronic.services.imple;

import com.roble.springproject.robleelectronic.models.Category;
import com.roble.springproject.robleelectronic.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void save() {

        //Given
        Category cat = new Category();
        cat.setId(1L);
        cat.setDescription("Some description!!!1");

        //when
        when(categoryRepository.save(any())).thenReturn(cat);

        Category savedCat = categoryService.save(cat);

        //then
        verify(categoryRepository).save(any());
        assertNotNull(savedCat);
        assertEquals(1L, savedCat.getId());

    }
}