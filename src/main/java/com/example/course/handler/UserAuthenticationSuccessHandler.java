package com.example.course.handler;

import com.example.course.domain.History;
import com.example.course.repository.HistoryRepository;
import com.example.course.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final HistoryRepository historyRepository;
    private final MemberService memberService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String agent = request.getHeader("user-agent");
        String id = authentication.getName();
        LocalDateTime now = LocalDateTime.now();
        memberService.updateLastLoginDate(id, now);
        historyRepository.save(History.builder()
                .memberUserId(id)
                .loginDate(now)
                .loginIp(request.getRemoteAddr())
                .agent(agent)
                .build());
        response.sendRedirect("/");
    }
}
