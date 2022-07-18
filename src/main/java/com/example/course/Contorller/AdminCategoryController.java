package com.example.course.Contorller;

import com.example.course.model.category.AddCategory;
import com.example.course.model.category.CategoryDto;
import com.example.course.model.category.UpdateCategory;
import com.example.course.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/admin/categories")
    public String categories(Model model) {
        List<CategoryDto> categories = categoryService.selectAll();
        model.addAttribute("categories", categories);
        return "admin/categories";
    }

    @PostMapping("/admin/category/add.do")
    public String add(Model model, AddCategory.Request request) {
        categoryService.add(request.getCategoryName());
        System.out.println(request.getCategoryName());
        return "redirect:/admin/categories";
    }

    @PostMapping("/admin/category/update.do")
    public String modify(UpdateCategory.Request request) {
        categoryService.update(request.getId(),
                request.getCategoryName(),
                request.getSortValue(),
                request.getCategoryStatus());
        return "redirect:/admin/categories";
    }

    @PostMapping("/admin/category/{id}")
    public String remove(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/admin/categories";
    }
}
