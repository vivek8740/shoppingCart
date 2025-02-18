package com.ecom.service;

import java.util.List;

import com.ecom.model.Cart;

public interface CartService {

    public Cart saveToCart(Long userId,Integer productId);

    public List<Cart> getCartByUser(Long userId);

    public Integer getCartProductCount(Long userId);

}
