/*
package com.roble.springproject.robleelectronic.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.roble.springproject.robleelectronic.security.UserRoles.*;

@Repository("fake")
public class FakeUserDaoImpl implements UserDao {

    private final PasswordEncoder passwordEncoder;

    public FakeUserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserPrinciples> findByUsername(String username) {
        return getAllUsers().stream()
                .filter(user -> user.equals(username))
                .findFirst();
    }

    public List<UserPrinciples> getAllUsers(){
        List<UserPrinciples> users = Arrays.asList(
                new UserPrinciples(
                  "liban",
                        passwordEncoder.encode( "password123"),
                        ADMIN.getGrantedAuthority(),
                        true,
                        true,
                        true,
                        true
                ),
                new UserPrinciples(
                        "zamin",
                        passwordEncoder.encode( "password123"),
                        USER.getGrantedAuthority(),
                        true,
                        true,
                        true,
                        true
                )
        );

        return users;
    }
}
*/
