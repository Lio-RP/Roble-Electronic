package com.roble.springproject.RobleElectronic.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product extends BaseEntity {

    @Column(name = "name")
    @NotBlank
    @Size(min = 10, max = 255)
    private String name;

    @Column(name = "description")
    @Lob
    @NotBlank
    private String description;

    @Column(name = "brand")
    @NotBlank
    @Size(min = 3, max = 255)
    private String brand;

    @Column(name = "price")
    @Positive
    private float price;

    @Column(name = "in_stock")
    @Positive
    private int inStock;

    @Column(name = "image")
    @Lob
    private Byte[] image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", inStock=" + inStock +
                ", image=" + Arrays.toString(image) +
                ", category=" + category +
                '}';
    }
}
