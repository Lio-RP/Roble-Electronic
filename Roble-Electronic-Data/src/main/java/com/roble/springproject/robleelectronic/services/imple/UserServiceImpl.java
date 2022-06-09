package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.auth.UserPrinciples;
import com.roble.springproject.RobleElectronic.models.Role;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.repositories.UserRepository;
import com.roble.springproject.RobleElectronic.services.RoleService;
import com.roble.springproject.RobleElectronic.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User register(User user) {

        User username = userRepository.findByUserName(user.getUserName());

        if(emailExists(user.getEmail()) || username != null){
            throw new RuntimeException("the email or usename already exist");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        Role user_role = roleService.findByRole("USER");
        user.getRoles().add(user_role);
        user.setRegDate(LocalDate.now());
        user.setPassword(encodedPassword);

//        User savedUser =  userRepository.create(user.getFirstName(), user.getLastName(), user.getUserName(),
//                user.getEmail(), user.getPassword(), user.getRegDate());
        return userRepository.save(user);
    }

    @Override
    public boolean emailExists(String email){
        return findByEmail(email) != null;
    }

    @Override
    public User getCurrentlyLoggedUser(UserPrinciples userPrincipal) {

        if(userPrincipal == null) return null;

        return userPrincipal.getUser();
    }
}
