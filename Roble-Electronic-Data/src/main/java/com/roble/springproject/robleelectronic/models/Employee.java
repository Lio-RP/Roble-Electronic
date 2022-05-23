package com.roble.springproject.RobleElectronic.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employee")
public class Employee extends Person {

    @Column(name = "jop_title")
    @NotBlank
    @Size(min = 5, max = 255)
    private String jopTitle;

    public String getJopTitle() {
        return jopTitle;
    }

    public void setJopTitle(String jopTitle) {
        this.jopTitle = jopTitle;
    }
}
