package com.ecom.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.model.User;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;

@Service
public class UserServiceImpl implements UserService{

     @Autowired
    private UserRepository userRepository;

    // Register new user
    @Transactional
    @Override
    public User registerUser(User user) {
        // You might want to perform additional checks here (e.g., check if email already exists)
        return userRepository.save(user);
    }

}
