package com.roble.springproject.RobleElectronic.repositories;

import com.roble.springproject.RobleElectronic.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE user.userName =:userName")
    User findByUserName(@Param("userName") String userName);

    @Query("SELECT user FROM User user WHERE user.email = :email")
    User findByEmail(@Param("email") String email);

}
