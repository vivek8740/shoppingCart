package com.ecom.service;

import java.util.List;

import com.ecom.model.User;

public interface UserService {

    public User registerUser(User user);

    public User findUserByEmail(String email);

    List<User> getAllUsers();

    List<User> getAllActiveUsers();

    List<User> getAllUsers(String role);

    public Boolean updateUserAccount(Long id, String status);

}
