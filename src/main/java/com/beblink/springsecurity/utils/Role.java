package com.beblink.springsecurity.utils;


import java.util.Arrays;
import java.util.List;

/**
 * The enum Role.
 */
public enum Role {

    /**
     * Customer role.
     */
    CUSTOMER(List.of(Permission.READ_ALL_PRODUCTS)),

    /**
     * Administrator role.
     */
    ADMINISTRATOR(Arrays.asList(Permission.SAVE_ONE_PRODUCT, Permission.READ_ALL_PRODUCTS));

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * Gets permissions.
     *
     * @return the permissions
     */
    public List<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Sets permissions.
     *
     * @param permissions the permissions
     */
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
