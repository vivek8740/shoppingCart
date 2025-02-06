package com.ecom.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.ecom.helper.HelperServiceImpl;
import com.ecom.model.Category;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;

@WebMvcTest(AdminController.class)
@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserService userService;

    @MockBean
    private HelperServiceImpl helper;

    @Test
    public void testGetCategory() throws Exception {
        List<Category> categories = Arrays.asList(new Category(1, "Electronics", "image.jpg", true));
        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/admin/category"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/category"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    public void testCreateCategory() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", new byte[0]);
        Category category = new Category(1, "Books", "image.jpg", true);

        when(categoryService.isCategoryExist(anyString())).thenReturn(false);
        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        mockMvc.perform(multipart("/admin/saveCategory")
                        .file(file)
                        .param("categoryName", "Books")
                        .param("isActive", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/category"));
    }

    @Test
    public void testUpdateCategory() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", new byte[0]);
        Category category = new Category(1, "Updated Name", "image.jpg", true);

        when(categoryService.updateCategory(any(Category.class))).thenReturn(category);

        mockMvc.perform(multipart("/admin/updateCategory")
                        .file(file)
                        .param("categoryId", "1")
                        .param("isActive", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/loadEditCategory/1"));
    }
}
