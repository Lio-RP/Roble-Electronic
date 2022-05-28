package com.roble.springproject.RobleElectronic.auth;

import com.roble.springproject.RobleElectronic.models.Privilege;
import com.roble.springproject.RobleElectronic.models.Role;
import com.roble.springproject.RobleElectronic.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrinciples implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        List<Role> roles = user.getRoles();

        for(Role role : roles){
            authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));

            for(Privilege privilege : role.getPrivileges()){
                authorityList.add(new SimpleGrantedAuthority(privilege.getName()));
            }
        }

        return authorityList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
