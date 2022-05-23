package com.roble.springproject.RobleElectronic.repositories;

import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.models.User_Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepository extends CrudRepository<User_Role, Long> {

    @Query("SELECT user FROM User_Role user WHERE user.user = :user")
    User_Role findByUser(@Param("user") User user);
}
