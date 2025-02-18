package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.model.Cart;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    public Cart findByProduct_ProductIdAndUser_Id(Integer productId,Long userId);

    public Integer countByUserId(Long userId);

    public List<Cart> findByUser_Id(Long userId);

}
