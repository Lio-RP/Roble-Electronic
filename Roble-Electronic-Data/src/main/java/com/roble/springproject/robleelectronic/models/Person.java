package com.roble.springproject.RobleElectronic.models;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@MappedSuperclass
public class Person extends BaseEntity {

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


}
