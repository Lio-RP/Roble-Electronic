package com.roble.springproject.robleelectronic.services;

import com.roble.springproject.robleelectronic.models.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    Category save(Category object);

    List<Category> getAllCategories();

    Category getCategoryById(Long categoryId);

    Category findByDescription(String description);

    void deleteById(Long id);
}
