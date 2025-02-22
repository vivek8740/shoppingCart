package com.ecom.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ecom.model.Cart;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.repository.CartRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import com.ecom.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart saveToCart(Long userId, Integer productId) {
        User user = userRepository.findById(userId).get();
        Product product = productRepository.findById(productId).get();
        Cart availableCart = cartRepository.findByProduct_ProductIdAndUser_Id(productId, userId);

        Cart cart = null;
        if (ObjectUtils.isEmpty(availableCart)) {
            cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(1);
            cart.setTotalPrice(1 * product.getDiscountPrice());

        } else {
            cart = availableCart;
            cart.setQuantity(cart.getQuantity() + 1);
            cart.setTotalPrice(cart.getQuantity() * cart.getProduct().getDiscountPrice());
        }
        Cart savedCart = cartRepository.save(cart);
        return savedCart;
    }

    @Override
    public List<Cart> getCartByUser(Long userId) {
        List<Cart> cartList = cartRepository.findByUser_Id(userId);
        Double totalOrderPrice = 0.0;
        for (Cart cart : cartList) {
            Double price = cart.getProduct().getDiscountPrice() * cart.getQuantity();
            cart.setTotalPrice(price);
            totalOrderPrice = totalOrderPrice + price;
            cart.setTotalOrderPrice(totalOrderPrice);
        }
        return cartList;
    }


    @Override
    public Integer getCartProductCount(Long userId) {
        Integer count = cartRepository.countByUserId(userId);
        return count;
    }

    @Override
    public void updateCartQuantity(Integer cartId, String sy) {
        Cart userCart = cartRepository.findById(cartId).get();
        int newQuantity = 0;

        if(sy.equals("de")){
           newQuantity  = userCart.getQuantity() - 1;
           if(newQuantity <= 0){
            cartRepository.delete(userCart);
           }else{
            userCart.setQuantity(newQuantity);
            cartRepository.save(userCart);
           }
        }
        else{
            newQuantity = userCart.getQuantity() + 1;
            userCart.setQuantity(newQuantity);
            cartRepository.save(userCart);
        }

    }

    @Override
    public User getUserByCartId(Integer cartId) {
        User user =  cartRepository.findUserByCartId(cartId);
        return user;

    }

    

    
}
