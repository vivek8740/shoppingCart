package com.ecom.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/")
    public String getIndex() {
        logger.info("Accessed the home page.");
        return "index";
    }

    @GetMapping(value = "/login")
    public String getLogin() {
        logger.info("Accessed the login page.");
        return "login";
    }

    @GetMapping(value = "/register")
    public String getRegister() {
        logger.info("Accessed the register page.");
        return "register";
    }

    @GetMapping(value = "/products")
    public String getProducts(Model model, @RequestParam(value = "category", defaultValue = "") String category) {
        logger.info("Accessed the products page with category filter: {}", category);

        // Fetch products and categories
        List<Product> products = productService.getAllActiveProductsBasedOnCategory(category);
        //List<Product> products = productService.getAllActiveProducts();
        List<Category> categories = categoryService.getActiveCategories();

        // Log the number of products and categories retrieved
        logger.info("Retrieved {} products and {} categories.", products.size(), categories.size());

        // Add attributes to model
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("paramValue", category);

        return "product";
    }

    @GetMapping(value = "/product")
    public String getProduct() {
        logger.info("Accessed the product details page.");
        return "view_product";
    }
}
