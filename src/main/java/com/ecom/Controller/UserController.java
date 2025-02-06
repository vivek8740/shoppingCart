package com.ecom.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecom.model.Category;
import com.ecom.model.User;
import com.ecom.service.CategoryService;
import com.ecom.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;
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

}
