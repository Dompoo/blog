package dompoo.blog.controller;

import dompoo.blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        String login = memberService.login();
        return login;
    }
}
