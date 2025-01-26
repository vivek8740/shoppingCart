package com.ecom.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

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
    public String getProducts(Model model){
        List<Product> products = productService.getAllActiveProducts();
        List<Category> categories = categoryService.getActiveCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "product";
    }

    @GetMapping(value = "/product")
    public String getProduct(){
        return "view_product";
    }


}
