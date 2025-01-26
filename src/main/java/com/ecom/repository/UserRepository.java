package com.ecom.repository;

import com.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom queries if needed (e.g., findByEmail, findByMobileNumber)
    User findByEmail(String email);
    User findByMobileNumber(String mobileNumber);
}
