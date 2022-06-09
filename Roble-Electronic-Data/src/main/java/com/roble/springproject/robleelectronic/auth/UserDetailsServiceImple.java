package com.roble.springproject.RobleElectronic.auth;

import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImple implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("User Not Found");
        }
        UserPrinciples userPrinciples = new UserPrinciples();
        userPrinciples.setUser(user);
        return userPrinciples;
    }
}

