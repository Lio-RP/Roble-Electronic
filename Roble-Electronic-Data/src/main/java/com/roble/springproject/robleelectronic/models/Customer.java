package com.roble.springproject.RobleElectronic.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends Person {

    @Column(name = "address_line1")
    @NotBlank
    @Size(min = 5, max = 255)
    private String addressLine1;

    @Column(name = "city")
    @NotBlank
    @Size(min = 5, max = 255)
    private String city;

    @Column(name = "country")
    @NotBlank
    @Size(min = 5, max = 255)
    private String country;

    @Column(name = "zipcode")
    @NotBlank
    @Size(min = 5, max = 10)
    private String zipcode;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

   public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
