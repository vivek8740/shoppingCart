package com.ecom.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String getIndex(){
        return "index";
    }

    @GetMapping(value = "/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping(value = "/register")
    public String getRegister(){
        return "register";
    }

    @GetMapping(value = "/products")
    public String getProducts(){
        return "product";
    }

    @GetMapping(value = "/product")
    public String getProduct(){
        return "view_product";
    }


}
