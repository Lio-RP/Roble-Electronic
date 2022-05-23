package com.roble.springproject.RobleElectronic.services;

import com.roble.springproject.RobleElectronic.models.Category;

import java.util.List;

public interface CategoryService {

    Category save(Category object);

    List<Category> getAllCategories();

    Category getCategoryById(Long categoryId);

    Category findByDescription(String description);

    void deleteById(Long id);

    void deleteAll();
}
