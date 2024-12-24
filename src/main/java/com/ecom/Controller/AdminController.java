package com.ecom.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Category;
import com.ecom.service.CategoryService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

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

    @PostMapping("/saveCategory")
    public String createCategory(@ModelAttribute Category category,
                                 @RequestParam("file") MultipartFile file,
                                 HttpSession session){
        String imageName = file != null ? file.getOriginalFilename() : "default.jpg"; 
        category.setImageName(imageName);
        Boolean categoryExist = categoryService.isCategoryExist(category.getCategoryName());
        
        if(categoryExist){
            session.setAttribute("errorMsg", "Category Already Exist!!!");
        }else{
            Category savedCategory = categoryService.createCategory(category);
            if (ObjectUtils.isEmpty(savedCategory))
                session.setAttribute("errorMsg", "Not Saved,Something Wrong!!!");
            else
            session.setAttribute("successMsg", "Category Saved...");
        }
        return "redirect:/admin/category";
    }
    

}
