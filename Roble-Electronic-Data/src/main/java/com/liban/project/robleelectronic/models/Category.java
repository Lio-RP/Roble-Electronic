package com.liban.project.robleelectronic.models;

import java.util.HashSet;
import java.util.Set;

public class Category extends BaseEntity {

    private String description;

    //OneToMany
    private Set<Product> products = new HashSet<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
