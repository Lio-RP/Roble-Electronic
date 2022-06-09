package com.roble.springproject.RobleElectronic.repositories;

import com.roble.springproject.RobleElectronic.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE user.userName =:userName")
    User findByUserName(@Param("userName") String userName);

    @Query("SELECT user FROM User user WHERE user.email = :email")
    User findByEmail(@Param("email") String email);

/*    @Modifying
    @Query(value = "insert into user (first_name, last_name, user_name, email, password, reg_date) values (:first_name, :last_name, :user_name, :email, :password, :reg_date)",
    nativeQuery = true)
    User create(@Param("first_name") String first_name, @Param("last_name") String last_name,
              @Param("user_name") String user_name, @Param("email") String email,
              @Param("password") String password, @Param("reg_date")LocalDate reg_date);*/
}
