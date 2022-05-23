package com.roble.springproject.RobleElectronic.models;

import javax.persistence.*;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart extends BaseEntity {

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
