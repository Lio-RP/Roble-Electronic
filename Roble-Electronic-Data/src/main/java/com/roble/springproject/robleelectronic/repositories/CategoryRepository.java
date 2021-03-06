package com.roble.springproject.RobleElectronic.repositories;

import com.roble.springproject.RobleElectronic.models.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query("SELECT category FROM Category category WHERE LOWER(category.description) = LOWER(:description)")
    @Transactional(readOnly=true)
    Category findByDescription(String description);
}
