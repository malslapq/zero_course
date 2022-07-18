package com.example.course.Contorller;

import com.example.course.model.course.AddCourse;
import com.example.course.model.category.CategoryDto;
import com.example.course.model.course.CourseDto;
import com.example.course.model.course.SearchCourses;
import com.example.course.service.CategoryService;
import com.example.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequiredArgsConstructor
@Controller
public class AdminCourseController {

    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping("/admin/courses")
    public String courses(Model model, SearchCourses.Request request,
                          @PageableDefault Pageable pageable) {

        Page<CourseDto> courses = courseService.selectAll(
                request.getSearchType(),
                request.getSearchValue(),
                pageable);

        List<CategoryDto> categories = categoryService.selectAll();

        model.addAttribute("courses", courses.getContent());
        model.addAttribute("categories", categories);
        model.addAttribute("totalPage", courses.getTotalPages());
        return "admin/course/courses";
    }

    @GetMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String addForm(Model model, Long id, HttpServletRequest request) {
        List<CategoryDto> categories = categoryService.selectAll();
        CourseDto courseDto = new CourseDto();
        boolean mode = false;
        if (request.getRequestURI().contains("/edit.do")) {
            courseDto = courseService.select(id);
            mode = true;
        }
        model.addAttribute("categories", categories);
        model.addAttribute("course", courseDto);
        model.addAttribute("mode", mode);
        return "admin/course/add";
    }

    @PostMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String add(AddCourse.Request request, HttpServletRequest httpServletRequest) {

        if (httpServletRequest.getRequestURI().contains("/edit.do")) {
            courseService.modify(request.getId(), request);
        } else {
            courseService.insert(request, request.getCategoryId());
        }
        return "redirect:/admin/courses";
    }


}
