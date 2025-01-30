package com.ecom.service;

import com.ecom.model.User;

public interface UserService {

    public User registerUser(User user);

    public User findUserByEmail(String email);

}
