package com.ecom.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.helper.HelperServiceImpl;
import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    HelperServiceImpl helper;

    @ModelAttribute
    public void getUserDetails(Principal principal, Model model) {
        //To show user in banner.
        if (principal != null) {
            String email = principal.getName();
            User loggedUser = userService.findUserByEmail(email);
            model.addAttribute("user", loggedUser);
        }

        List<Category> categories = categoryService.getActiveCategories();

        //To show active catogries in banner
        model.addAttribute("categories", categories);
    }


    @GetMapping("/")
    public String getIndex() {
        return "admin/index";
    }

    @GetMapping("/category")
    public String getCategory(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/category";
    }

    @PostMapping("/saveCategory")
    public String createCategory(@ModelAttribute Category category,
            @RequestParam("file") MultipartFile file,
            HttpSession session) throws IOException {
        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
        category.setImageName(imageName);
        Boolean categoryExist = categoryService.isCategoryExist(category.getCategoryName());

        if (categoryExist) {
            session.setAttribute("errorMsg", "Category Already Exist!!!");
        } else {
            Category savedCategory = categoryService.createCategory(category);
            if (ObjectUtils.isEmpty(savedCategory))
                session.setAttribute("errorMsg", "Not Saved,Something Wrong!!!");
            else {

                if (!file.isEmpty()) {
                    helper.saveFileToPath(file, "category_img");
                }
                session.setAttribute("successMsg", "Category Saved...");
            }
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCatesory(@PathVariable int id,
            HttpSession session) {
        boolean status = categoryService.deleteCategory(id);
        if (status)
            session.setAttribute("successMsg", "Category Deleted...");
        else
            session.setAttribute("errorMsg", "Something Went Wrong ...");
        return "redirect:/admin/category";
    }

    @GetMapping("/loadEditCategory/{id}")
    public String loadEditCategory(@PathVariable int id, Model model) {

        model.addAttribute("category", categoryService.findCatetoryById(id));
        return "admin/edit_category";
    }

    @PostMapping("/updateCategory")
    public String updateeCategory(@ModelAttribute Category category,
            @RequestParam("file") MultipartFile file,
            HttpSession session) throws IOException {

        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
        category.setImageName(imageName);
        Category updatedCategory = categoryService.updateCategory(category);
        if (!ObjectUtils.isEmpty(updatedCategory)) {
            File saveFile = new ClassPathResource("static/img").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
                    + file.getOriginalFilename());
            System.out.println(path);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            session.setAttribute("successMsg", "Category Updated!!!");
        } else {
            session.setAttribute("errorMsg", "Something Went Wrong ...");
        }
        return "redirect:/admin/loadEditCategory/" + category.getCategoryId();
    }

    /************************* Product module *******************************/
    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute Product product,
            @RequestParam("file") MultipartFile file,
            HttpSession session) throws IOException {

        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
        product.setProductImage(imageName);
        Product savedProduct = productService.saveProduct(product);

        if (!ObjectUtils.isEmpty(savedProduct)) {
            if (!file.isEmpty())
                helper.saveFileToPath(file, "product_img");

            session.setAttribute("successMsg", "Product saved...");

        } else {
            session.setAttribute("errorMsg", "Something went wrong!!!");
        }

        return "redirect:/admin/loadAddProduct";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "admin/add_product";

    }

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<Product> prodList = productService.getAllProducts();
        model.addAttribute("products", prodList);
        return "admin/products";

    }

    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/edit_product";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id,
            HttpSession session) {
        boolean status = productService.deleteProduct(id);
        if (status)
            session.setAttribute("successMsg", "Category Deleted...");
        else
            session.setAttribute("errorMsg", "Something Went Wrong ...");
        return "redirect:/admin/products";
    }

    @PostMapping("/updateProduct")
    public String updateProduc(@ModelAttribute Product product,
            @RequestParam("file") MultipartFile file,
            HttpSession session) throws IOException {

        Product updatedproduct = null;
        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
        product.setProductImage(imageName);

        boolean validDiscount = helper.validateDiscountValue(product.getDiscount());
        if (!validDiscount) {
            session.setAttribute("errorMsg", "Invalid Discount");
            return "redirect:/admin/editProduct/" + product.getProductId();
        } else {
            updatedproduct = productService.updateProduct(product);

            if (!ObjectUtils.isEmpty(updatedproduct)) {
                if (!file.isEmpty())
                    helper.saveFileToPath(file, "product_img");

                session.setAttribute("successMsg", "Product updated...");

            } else {
                session.setAttribute("errorMsg", "Something went wrong!!!");
            }
        }

        return "redirect:/admin/editProduct/" + updatedproduct.getProductId();
    }


    /**************** User ********************/
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers("ROLE_USER"));
        return "admin/view_users";
    }

    @GetMapping("/updateStatus")
    public String updateUserAccountStatus(@RequestParam String status, @RequestParam Long id ,HttpSession session){
        Boolean isModified = userService.updateUserAccount(id , status);
        if (isModified)
            session.setAttribute("successMsg", "User status updated.");
        else
            session.setAttribute("errorMsg", "Something Went Wrong!!!");
        return "redirect:/admin/users";
    }

}
