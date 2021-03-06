package com.roble.springproject.RobleElectronic.models;

import com.roble.springproject.RobleElectronic.enums.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "order_number")
    private String orderNumber;
    
    @Column(name = "date_ordered")
    private LocalDate dateOrdered;

    @ManyToMany
    @JoinTable(name = "order_product", joinColumns = @JoinColumn(name = "order_id"),
                        inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();
    
    @Column(name = "product_quantity")
    private int productQuantity;

    @Column(name = "total_price")
    private float totalPrice;
    
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(LocalDate dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
