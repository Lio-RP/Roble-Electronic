package com.roble.springproject.robleelectronic.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_line")
public class ShoppingCart extends BaseEntity {

    @Column(name = "quantity")
    private int quantity;

    @OneToOne
    private Product product;

    @Column(name = "subtotal")
    private float subtotal;

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

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }
}
