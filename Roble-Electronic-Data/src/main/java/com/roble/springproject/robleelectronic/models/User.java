package com.roble.springproject.robleelectronic.models;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@MappedSuperclass
public class User extends BaseEntity {

    @Column(name = "first_name")
    @NotBlank
    @Size(min = 25, max = 255)
    private String firstName;
    
    @Column(name = "last_name")
    @NotBlank
    @Size(min = 25, max = 255)
    private String lastName;
    
    @Column(name = "email")
    @Email
    private String email;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "reg_date")
    private LocalDate regDate;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }
}
