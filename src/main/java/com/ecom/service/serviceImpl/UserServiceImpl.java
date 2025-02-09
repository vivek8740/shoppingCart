package com.ecom.service.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.ecom.helper.AppConstants;
import com.ecom.helper.HelperService;
import com.ecom.model.User;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    HelperService helperService;

    // Register new user
    @Transactional
    @Override
    public User registerUser(User user) {
       user.setRole("ROLE_USER");
       user.setEnabled(true);
       user.setAccountNonLocked(true);
       user.setFailedAttempt(0);
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

    @Override
    public void increaseFailedAttempt(User user) {
        int attempt = user.getFailedAttempt() + 1;
        user.setFailedAttempt(attempt);
        userRepository.save(user);
    }

    @Override
    public void restAttempt(Long userId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean unLockAccountTimeExpired(User user) {
       long lockTime = user.getLockTime().getTime();
       long unlockTime = lockTime + AppConstants.UNLOCK_DURATION_TIME;
       long curretTime = System.currentTimeMillis();
       if(curretTime < unlockTime){
            user.setAccountNonLocked(true);
            user.setFailedAttempt(0);
            user.setLockTime(null);
            userRepository.save(user);
            return true;
       }
       return false;
    }

    @Override
    public void userAccountLock(User user) {

        user.setAccountNonLocked(false);
        user.setLockTime(new Date());
        userRepository.save(user);
        
    }

    @Override
    public void updateResetToken(String email, String token) {
        User user = userRepository.findByEmail(email);
        if (!ObjectUtils.isEmpty(user)) {
            user.setResetToken(token);
            userRepository.save(user);
        }
        else
        throw new UsernameNotFoundException("User not available");
    }

    @Override
    public User getUserByToken(String token) {
        User user_based_on_token = userRepository.findByResetToken(token);
        if(ObjectUtils.isEmpty(user_based_on_token))
            throw new UsernameNotFoundException("User with provided token not available");
        
        return user_based_on_token;
    }

    @Override
    public boolean resetUserPassword(String token, String password) {
        User user = getUserByToken(token);
        user.setPassword(encoder.encode(password));
        user.setResetToken(null);
        userRepository.save(user);
        return true;
    }
    
}
