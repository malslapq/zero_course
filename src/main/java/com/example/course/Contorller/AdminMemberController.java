package com.example.course.Contorller;

import com.example.course.model.history.HistoryDto;
import com.example.course.model.member.ChangeMemberStatus;
import com.example.course.model.member.MemberDto;
import com.example.course.model.member.MemberResetPassword;
import com.example.course.model.member.SearchMembers;
import com.example.course.service.HistoryService;
import com.example.course.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class AdminMemberController {

    private final MemberService memberService;
    private final HistoryService historyService;

    @GetMapping("/admin/members")
    public String members(Model model,
                             SearchMembers.Request request,
                             @PageableDefault(size = 10) Pageable pageable) {

        Page<MemberDto> members = memberService.selectAll(
                request.getSearchType(),
                request.getSearchValue(),
                pageable);

        long rest = members.getTotalElements() % members.getSize();
        int endNo = members.isFirst() ?
                (int) members.getTotalElements() :
                (members.getTotalPages() - members.getNumber() - 1) * members.getSize() + (int) rest;
//        int startNo = Math.max(endNo - members.getSize() + 1, 1);

        model.addAttribute("startRowNum",
                members.getPageable().getPageNumber() + 1);
        model.addAttribute("members", members.getContent());
        model.addAttribute("totalPage", members.getTotalPages());
        model.addAttribute("endNo", endNo);
        return "admin/members";
    }

    @GetMapping("/admin/member/detail.do")
    public String memberDetail(String userId, Model model) {

        MemberDto memberDto = memberService.detail(userId);
        List<HistoryDto> histories = historyService.selectAll(userId);
        model.addAttribute("member", memberDto);
        model.addAttribute("histories", histories);

        return "admin/detail";
    }

    @PostMapping("/admin/member/status.do")
    public String status(ChangeMemberStatus.Request request, Model model) {
        MemberDto memberDto = memberService.updateStatus(
                request.getUserId(), request.getUserStatus());
        model.addAttribute("member", memberDto);
        return "admin/detail";
    }

    @PostMapping("/admin/member/reset/password")
    public String resetPassword(MemberResetPassword.Request request) {

        memberService.sendResetPassword(request.getUserId(), request.getUserName());
        return "redirect:/admin/member/detail.do?userId="+request.getUserId();
    }
}
