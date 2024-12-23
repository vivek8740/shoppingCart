package com.ecom.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    public String getIndex() {
        return "admin/index";
    }

    @GetMapping("/addProduct")
    public String addProduct() {
        return "admin/add_product";
    }

    @GetMapping("/category")
    public String getCategory() {
        return "admin/add_category";
    }
    

}
