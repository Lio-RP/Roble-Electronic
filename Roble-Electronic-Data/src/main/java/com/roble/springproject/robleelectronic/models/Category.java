package com.roble.springproject.robleelectronic.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category extends BaseEntity {

    @Column(name = "description")
    @Lob
    @NotBlank
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
