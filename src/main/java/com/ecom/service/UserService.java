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


    public void increaseFailedAttempt(User user);
    public void userAccountLock(User user);
    public boolean unLockAccountTimeExpired(User user);
    public void restAttempt(Long userId);

    public void updateResetTOken(String email, String token);
}
