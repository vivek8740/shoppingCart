package com.ecom.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.helper.HelperService;
import com.ecom.model.Cart;
import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    HelperService helperService;

    @ModelAttribute
    public void getUserDetails(Principal principal , Model model){
        if(principal != null){
            String email = principal.getName();
            User loggedUser = userService.findUserByEmail(email);
            model.addAttribute("user", loggedUser);
            Integer cartCount = cartService.getCartProductCount(loggedUser.getId());
            model.addAttribute("cartcount", cartCount);
        }

        List<Category> categories = categoryService.getActiveCategories();

        //To show active catogries in banner
        model.addAttribute("categories", categories);
    }

    @GetMapping(value = "/")
    public String getIndex(Model model) {

        List<Category> categories = categoryService.getActiveCategories();
        model.addAttribute("categories", categories);

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

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user,
            @RequestParam("img") MultipartFile file,
            HttpSession session) throws IOException {

        String profileImage = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
        user.setProfileImage(profileImage);
        User savedUser = userService.registerUser(user);
        if (!ObjectUtils.isEmpty(savedUser)) {
            if (!file.isEmpty()) {
                helperService.saveFileToPath(file, "profile_img");
            }
            session.setAttribute("successMsg", "User Registered Successfully");
        } else {
            session.setAttribute("errorMsg", "Someting went wrong.");
        }

        return "redirect:/register";

    }

    @GetMapping(value = "/products")
    public String getProducts(Model model, @RequestParam(value = "category", defaultValue = "") String category) {
        logger.info("Accessed the products page with category filter: {}", category);

        // Fetch products and categories
        List<Product> products = productService.getAllActiveProductsBasedOnCategory(category);
        // List<Product> products = productService.getAllActiveProducts();
        List<Category> categories = categoryService.getActiveCategories();

        // Log the number of products and categories retrieved
        logger.info("Retrieved {} products and {} categories.", products.size(), categories.size());

        // Add attributes to model
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("paramValue", category);

        return "product";
    }

    @GetMapping(value = "/product/{id}")
    public String getProduct(@PathVariable int id, Model model) {
        logger.info("Accessed the product details page.");
        Product productByID = productService.getProductById(id);
        model.addAttribute("product", productByID);
        return "view_product";
    }

    @GetMapping("/forget-password")
    public String showForgetPasswordPage(){
        return "/user/forget-password";

    }

    @PostMapping("/forget-password")
    public String processForgetPasswordPage(@RequestParam String email,HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException, MessagingException{
        User user = userService.findUserByEmail(email);
        if(ObjectUtils.isEmpty(user)){
            session.setAttribute("errorMsg", "Invalid Email.");
        }
        else{
            String token = UUID.randomUUID().toString();
            userService.updateResetToken(email,token);
            String url = helperService.generateURL(request)+"/reset-user-password?token="+token;
            Boolean status = helperService.sendMail(email,url);
            if(status){
                session.setAttribute("successMsg", "Reset links sent to the mail.");
            }
            else
            session.setAttribute("errorMsg", "Something wrong with server. Unable to send reset link.");
        }
        return "redirect:/forget-password";

    }

    @GetMapping("/reset-user-password")
    public String showResetPasswordPage(@RequestParam String token,HttpSession session,Model model){
        User user_based_on_token = userService.getUserByToken(token);
        if(ObjectUtils.isEmpty(user_based_on_token)){
            model.addAttribute("message", "Link Not valid or expired.");
            return "message";
        }
        model.addAttribute("token", token);
        return "/user/reset-password";

    }

    @PostMapping("/reset-user-password")
    public String resetPassword(@RequestParam String token,@RequestParam String password, HttpSession session,Model model){
        Boolean status = userService.resetUserPassword(token,password);
        if(!status){
            model.addAttribute("message", "Link Not valid or expired.");
            return "message";
        }
        model.addAttribute("message", "Password changes successfully.");
        return "message";

    }

    @GetMapping("/cart/{userId}")
    public String loadCartPage(Model model,@PathVariable Long userId){
        logger.info("Fetching cart items.");
        List<Cart> cartItems = cartService.getCartByUser(userId);
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }
}