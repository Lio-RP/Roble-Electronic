package com.roble.springproject.RobleElectronic.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Set;
import java.util.stream.Collectors;

import static com.roble.springproject.RobleElectronic.security.UserPermissions.*;

public enum UserRoles {

    USER(Sets.newHashSet(PRODUCT_READ)),
    ADMIN(Sets.newHashSet(PRODUCT_READ, PRODUCT_WRITE, CATEGORY_READ, CATEGORY_WRITE));

    private final Set<UserPermissions> permissions;

    UserRoles(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermissions> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority(){
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return simpleGrantedAuthorities;
    }
}
