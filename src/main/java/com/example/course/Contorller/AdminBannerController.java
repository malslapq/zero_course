package com.example.course.Contorller;

import com.example.course.model.banner.AddBanner;
import com.example.course.model.banner.BannerDto;
import com.example.course.service.BannerService;
import com.example.course.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class AdminBannerController {

    private final BannerService bannerService;
    private final FileService fileService;

    @GetMapping("/admin/banners")
    public String banners(@PageableDefault Pageable pageable, Model model) {
        Page<BannerDto> banners = bannerService.selectAll(pageable);
        long rest = banners.getTotalElements() % banners.getSize();
        int endNo = banners.isFirst() ?
                (int) banners.getTotalElements() :
                (banners.getTotalPages() - banners.getNumber() - 1) * banners.getSize() + (int) rest;
        model.addAttribute("startRowNum",
                banners.getPageable().getPageNumber() + 1);
        model.addAttribute("banners", banners.getContent());
        model.addAttribute("totalPage", banners.getTotalPages());
        model.addAttribute("endNo", endNo);
        return "admin/banner/banners";
    }

    @GetMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String addForm(Model model, Long id) {
        BannerDto bannerDto = new BannerDto();
        boolean mode = false;
        if (id != null) {
            bannerDto = bannerService.select(id);
            mode = true;
        }
        model.addAttribute("banner", bannerDto);
        model.addAttribute("mode", mode);
        return "admin/banner/add";
    }

    @PostMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String add(AddBanner.Request request, MultipartFile file, HttpServletRequest httpServletRequest) {
        System.out.println(request.isStatus());
        String imageName = UUID.randomUUID().toString() + ".jpg";
        if (httpServletRequest.getRequestURI().contains("/edit.do")) {
            String oldImgName = bannerService.modify
                    (request.getId(), request.getName(), request.getSortValue(), request.isStatus(), imageName);
            fileService.deleteFile(oldImgName);
        } else {
            bannerService.insert(request.getName(), imageName,
                    request.getSortValue(), request.isStatus());
        }
        fileService.saveFile(file, imageName);
        return "redirect:/admin/banners";
    }
}
