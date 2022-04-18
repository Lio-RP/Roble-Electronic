package com.roble.springproject.robleelectronic.services.imple;

import com.roble.springproject.robleelectronic.models.Category;
import com.roble.springproject.robleelectronic.repositories.CategoryRepository;
import com.roble.springproject.robleelectronic.services.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category object) {
        return categoryRepository.save(object);
    }
}
