package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.model.Cart;
import com.ecom.model.User;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    public Cart findByProduct_ProductIdAndUser_Id(Integer productId,Long userId);

    public Integer countByUserId(Long userId);

    public List<Cart> findByUser_Id(Long userId);

    // Find the user by cartId
    @Query("SELECT cart.user FROM Cart cart WHERE cart.cartId = :cartId")
    User findUserByCartId(@Param("cartId") Integer cartId);

}
