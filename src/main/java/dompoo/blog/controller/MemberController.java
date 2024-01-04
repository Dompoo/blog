package dompoo.blog.controller;

import dompoo.blog.request.form.MemberCreateForm;
import dompoo.blog.request.member.MemberInfoDto;
import dompoo.blog.request.member.MemberSaveDto;
import dompoo.blog.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup(Model model, MemberCreateForm memberCreateForm) {

        model.addAttribute("memberCreateForm", memberCreateForm);
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if (!memberCreateForm.getPassword().equals(memberCreateForm.getCheckPassword())) {
            bindingResult.rejectValue("checkPassword", "passwordInCorrect", "패스워드가 일치하지 않습니다.");
            return "signup_form";
        }
        try {
            memberService.join(new MemberSaveDto(
                    memberCreateForm.getUsername(),
                    memberCreateForm.getPassword()
            ));
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {

        return "login_form";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/info")
    public String info(Model model, Principal principal) {

        MemberInfoDto findMember = memberService.info(principal.getName());
        model.addAttribute("member", findMember);
        return "member_info";
    }
}
