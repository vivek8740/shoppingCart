package com.ecom.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecom.model.User;

/**
 * Custom implementation of the UserDetails interface used by Spring Security.
 * This class represents the user in the security context and provides necessary user-related information 
 * such as username, password, and roles/authorities required for authentication and authorization.
 */
public class CustomUser implements UserDetails {

    // The User model object that holds the user's information
    private User user;

    /**
     * Constructor to initialize the CustomUser object with a User object.
     * 
     * @param user the user to wrap into CustomUser
     */
    public CustomUser(User user) {
        this.user = user;
    }

    /**
     * This method returns the authorities (roles or permissions) granted to the user.
     * In this case, the user's role is fetched from the User object and wrapped 
     * into a SimpleGrantedAuthority.
     * 
     * @return a collection of GrantedAuthority representing the user's role
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Wrap the user's role into a SimpleGrantedAuthority
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

        // Return the authorities in a list
        return Arrays.asList(authority);
    }

    /**
     * This method returns the password of the user.
     * 
     * @return the user's password (which is stored in the User object)
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * This method returns the username (email) of the user, which Spring Security 
     * will use for authentication.
     * 
     * @return the user's email (which serves as the username)
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * This method checks if the user's account has expired. In this implementation, 
     * the account is always considered non-expired (true).
     * 
     * @return true, as this implementation assumes that the account never expires
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * This method checks if the user's account is locked. In this implementation,
     * the account is always considered non-locked (true).
     * 
     * @return true, as this implementation assumes that the account is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * This method checks if the user's credentials (password) have expired.
     * In this implementation, the credentials are always considered non-expired (true).
     * 
     * @return true, as this implementation assumes that credentials never expire
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * This method checks if the user's account is enabled. In this implementation, 
     * the account is always considered enabled (true).
     * 
     * @return true, as this implementation assumes that the account is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
