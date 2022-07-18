package com.example.course.Contorller;

import com.example.course.exception.MemberException;
import com.example.course.model.member.CreateMember;
import com.example.course.model.member.UpdateMember;
import com.example.course.model.member.ResetPasswordMember;
import com.example.course.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/member")
    public String save(CreateMember.Request request, Model model) {
        boolean result = memberService.insert(
                request.getUserId(),
                request.getUserName(),
                request.getPassword(),
                request.getPhone()
        );
        model.addAttribute("result", result);
        return "member/complete";
    }

    @GetMapping("/member/email-auth")
    public String emailAuth(@RequestParam String emailAuthKey) {
        memberService.emailAuth(emailAuthKey);
        return "member/emailAuth";
    }

    @GetMapping("/member/info")
    public String info() {
        return "member/info";
    }

    @RequestMapping("/member/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/member/find/password")
    public String findPassword() {
        return "member/find_password";
    }

    @PostMapping("/member/find/password")
    public String findPassword(UpdateMember.Request request, Model model) {
        boolean result = false;
        try {
            result = memberService.sendResetPassword(request.getUserId(),
                    request.getUserName());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        model.addAttribute("result", result);
        return "member/find_password_result";
    }

    @GetMapping("/member/reset/password")
    public String resetPassword(@RequestParam String resetPasswordKey, Model model) {
        model.addAttribute("resetPasswordKey", resetPasswordKey);
        return "member/reset_password";
    }

    @PostMapping("/member/reset/password")
    public String resetPassword(ResetPasswordMember.Request request, Model model) {
        boolean result = false;
        try {
            result = memberService.resetPassword(request.getPassword(), request.getResetPasswordKey());
        } catch (MemberException e) {
            log.error(e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("result", result);
        return "member/reset_password_result";
    }

}
