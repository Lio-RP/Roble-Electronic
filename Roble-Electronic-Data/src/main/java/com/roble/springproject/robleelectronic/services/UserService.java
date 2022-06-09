package com.roble.springproject.RobleElectronic.services;

import com.roble.springproject.RobleElectronic.auth.UserPrinciples;
import com.roble.springproject.RobleElectronic.models.User;

public interface UserService {

    User findByEmail(String email);

    User register(User user);

    User getCurrentlyLoggedUser(UserPrinciples userPrincipal);

    boolean emailExists(String email);
}
