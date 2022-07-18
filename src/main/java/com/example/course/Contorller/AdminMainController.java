package com.example.course.Contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMainController {

    @GetMapping("/admin")
    public String main() {
        return "admin/main";
    }



}
