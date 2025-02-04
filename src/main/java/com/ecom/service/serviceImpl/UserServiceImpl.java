package com.ecom.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.model.User;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    // Register new user
    @Transactional
    @Override
    public User registerUser(User user) {
       user.setRole("ROLE_USER");
       user.setEnabled(true);
       user.setPassword(encoder.encode(user.getPassword()));
       return userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllActiveUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllUsers(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public Boolean updateUserAccount(Long id, String status) {
        Optional<User> dbUser = userRepository.findById(id);
        if(dbUser.isPresent())
        {
            User user = dbUser.get();
            user.setEnabled(status.equals("active"));
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
