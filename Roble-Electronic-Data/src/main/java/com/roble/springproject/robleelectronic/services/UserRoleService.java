package com.roble.springproject.RobleElectronic.services;


import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.models.User_Role;

public interface UserRoleService {

    User_Role findByUser(User user);
}
