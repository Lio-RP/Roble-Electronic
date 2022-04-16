package com.liban.project.robleelectronic.models;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee extends User {

    @Column(name = "jop_title")
    private String jopTitle;

    public String getJopTitle() {
        return jopTitle;
    }

    public void setJopTitle(String jopTitle) {
        this.jopTitle = jopTitle;
    }
}
