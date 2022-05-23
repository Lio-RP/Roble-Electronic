package com.roble.springproject.RobleElectronic.auth;

import com.roble.springproject.RobleElectronic.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE user.userName =:userName")
    User findByUserName(@Param("userName") String userName);
}
