package com.roble.springproject.RobleElectronic.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {

    @Column(name = "first_name")
    @NotBlank
    @Size(min = 3, max = 255)
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Size(min = 3, max = 255)
    private String lastName;

    @Column(name = "phone_number")
    @NotBlank
    @Size(min = 11, max = 15)
    private String phoneNumber;

    @Column(name = "email")
    @NotBlank
    @Email
    private String email;

    @Column(name = "address")
    @NotBlank
    @Size(min = 5, max = 255)
    private String address;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
