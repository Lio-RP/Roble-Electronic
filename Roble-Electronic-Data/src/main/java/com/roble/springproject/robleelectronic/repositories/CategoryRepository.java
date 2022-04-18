package com.roble.springproject.robleelectronic.repositories;

import com.roble.springproject.robleelectronic.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
