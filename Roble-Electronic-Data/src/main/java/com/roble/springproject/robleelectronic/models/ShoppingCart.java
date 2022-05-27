package com.roble.springproject.RobleElectronic.models;

import javax.persistence.*;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart extends BaseEntity {

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "subtotal_price")
    private float subtotalPrice;

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

    public float getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(float subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "quantity=" + quantity +
                ", product=" + product +
                ", user=" + user +
                '}';
    }
}
