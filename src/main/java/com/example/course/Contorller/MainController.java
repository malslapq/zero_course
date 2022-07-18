package com.example.course.Contorller;

import com.example.course.config.MailComponents;
import com.example.course.model.banner.BannerDto;
import com.example.course.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final BannerService bannerService;

    @RequestMapping("/")
    public String index(Model model, Pageable pageable) {
        Page<BannerDto> bannerDtos = bannerService.selectAll(pageable);
        model.addAttribute("banners", bannerDtos.getContent());
        return "index";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello \r\n lms webSite!";
    }

    @RequestMapping("/error/denied")
    public String errorPage() {
        return "error/denied";
    }
}
