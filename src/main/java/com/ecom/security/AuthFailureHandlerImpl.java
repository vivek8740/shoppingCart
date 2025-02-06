package com.ecom.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.ecom.helper.AppConstants;
import com.ecom.model.User;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("username");
        User user = userRepository.findByEmail(email);

        if (user.isEnabled()) {
            if (user.getAccountNonLocked()) {
                if (user.getFailedAttempt() < AppConstants.MAX_ATTEMPT)
                    userService.increaseFailedAttempt(user);
                else {
                    userService.userAccountLock(user);
                    exception = new LockedException("Account is Locked.\n Maximum attempt reached.");
                }
            } else {
                if (userService.unLockAccountTimeExpired(user))
                    exception = new LockedException("Account is Unlock !! \n Try Login now.");
                else
                    exception = new LockedException("Account is Lock.\n Please wait for Sometime.");
            }
        } else {
            exception = new LockedException("Account Inactive.\n Contact Admin.");
        }
        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }

}
