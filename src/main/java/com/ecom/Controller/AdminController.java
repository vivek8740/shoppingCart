package com.ecom.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Category;
import com.ecom.service.CategoryService;

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

    @GetMapping("/")
    public String getIndex() {
        return "admin/index";
    }

    @GetMapping("/addProduct")
    public String addProduct() {
        return "admin/add_product";
    }

    @GetMapping("/category")
    public String getCategory(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/category";
    }

    @PostMapping("/saveCategory")
    public String createCategory(@ModelAttribute Category category,
                                 @RequestParam("file") MultipartFile file,
                                 HttpSession session) throws IOException{
        String imageName = file != null ? file.getOriginalFilename() : "default.jpg"; 
        category.setImageName(imageName);
        Boolean categoryExist = categoryService.isCategoryExist(category.getCategoryName());
        
        if(categoryExist){
            session.setAttribute("errorMsg", "Category Already Exist!!!");
        }else{
            Category savedCategory = categoryService.createCategory(category);
            if (ObjectUtils.isEmpty(savedCategory))
                session.setAttribute("errorMsg", "Not Saved,Something Wrong!!!");
            else{

                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+"category_img"+File.separator+file.getOriginalFilename());
                System.out.println(path);
                Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
                session.setAttribute("successMsg", "Category Saved...");
            }
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCatesory(@PathVariable int id,
                                  HttpSession session){
        boolean status = categoryService.deleteCategory(id);
        if (status) session.setAttribute("successMsg", "Category Deleted...");
        else session.setAttribute("errorMsg", "Something Went Wrong ...");
        return "redirect:/admin/category";
    }


    @GetMapping("/loadEditCategory/{id}")
    public String loadEditCategory(@PathVariable int id,Model model){

        model.addAttribute("category", categoryService.findCatetoryById(id));
        return "admin/edit_categoty";
    }

    @PostMapping("/updateCategory")
    public String updateeCategory(@ModelAttribute Category category,
                                  @RequestParam("file") MultipartFile file,
                                   HttpSession session){

        Category updatedCategory = categoryService.updateCategory(category);
        if (ObjectUtils.isEmpty(updatedCategory)){
            session.setAttribute("errorMsg", "Category Not Exist!!!");
        }else{
            session.setAttribute("errorMsg", "Something Went Wrong ...");
        }
        return "redirect:/admin/category";
    }
    

}
