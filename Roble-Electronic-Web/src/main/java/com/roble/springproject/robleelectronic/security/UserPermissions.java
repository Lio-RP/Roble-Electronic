package com.roble.springproject.robleelectronic.security;

public enum UserPermissions {
    PRODUCT_READ("product:read"),
    PRODUCT_WRITE("product:write"),
    CATEGORY_READ("category:read"),
    CATEGORY_WRITE("category:write");

    private final String permission;

    UserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
