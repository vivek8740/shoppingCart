package com.ecom.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecom.model.Cart;
import com.ecom.model.Category;
import com.ecom.model.User;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CartService cartService;
    
    @ModelAttribute
    public void getUserDetails(Principal principal , Model model){
        if(principal != null){
            String email = principal.getName();
            User loggedUser = userService.findUserByEmail(email);
            model.addAttribute("user", loggedUser);
        }

        List<Category> categories = categoryService.getActiveCategories();

        //To show active catogries in banner
        model.addAttribute("categories", categories);
    }

    @GetMapping("/home")
    public String home(){
        return "user/home";
    }

    @GetMapping("/addCart")
    public String addProductTouserCart(@RequestParam Long uid,@RequestParam Integer pid,HttpSession session){
        Cart cart = cartService.saveToCart(uid,pid);
        if(ObjectUtils.isEmpty(cart))
        session.setAttribute("errorMsg", "Product not added.");
        else
        session.setAttribute("successMsg", "Product added to cart.");
        return "redirect:/product/" + pid;
    }
}
