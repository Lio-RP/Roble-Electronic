package com.roble.springproject.RobleElectronic.auth;

import com.roble.springproject.RobleElectronic.models.Role;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.repositories.RoleRepository;
import com.roble.springproject.RobleElectronic.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;

@Service
public class UserDetailsServiceImple implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

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

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User register(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        Role user_role = roleService.findByRole("USER");
        user.getRoles().add(user_role);
        user.setRegDate(LocalDate.now());
        user.setPassword(encodedPassword);

//        User savedUser =  userRepository.create(user.getFirstName(), user.getLastName(), user.getUserName(),
//                user.getEmail(), user.getPassword(), user.getRegDate());
        User savedUser = userRepository.save(user);
        System.out.println(savedUser);
        return savedUser;
    }

    public User getCurrentlyLoggedUser(UserPrinciples userPrincipal){
        if(userPrincipal == null) return null;

        User user = userPrincipal.getUser();

       return user;
    }
}

