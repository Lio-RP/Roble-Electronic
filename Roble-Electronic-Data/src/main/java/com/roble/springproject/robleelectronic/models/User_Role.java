package com.roble.springproject.RobleElectronic.models;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class User_Role extends BaseEntity {

    @OneToOne
    private User user;

    @OneToOne
    private Role role;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
