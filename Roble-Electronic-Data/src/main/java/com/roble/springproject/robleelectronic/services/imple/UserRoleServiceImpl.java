package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.models.User_Role;
import com.roble.springproject.RobleElectronic.repositories.UserRoleRepository;
import com.roble.springproject.RobleElectronic.services.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public User_Role findByUser(User user) {
        return userRoleRepository.findByUser(user);
    }
}
