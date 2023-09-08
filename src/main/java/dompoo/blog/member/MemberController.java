package dompoo.blog.member;

import dompoo.blog.member.dto.MemberSaveDto;
import dompoo.blog.member.form.MemberCreateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;

//    //멤버 추가
//    @PutMapping("/members")
//    public MemberResponseDto addMember(@RequestBody MemberSaveDto dto) {
//        return memberService.join(dto);
//    }
//
//    //멤버 전체 조회
//    @GetMapping("/members")
//    public Page<MemberResponseDto> findMembers(@PageableDefault(size = 15) Pageable pageable) {
//        return memberService.findAll(pageable);
//    }
//
//    //id로 멤버 단건 조회
//    @GetMapping("/members/{memberId}")
//    public MemberResponseDto findMember(@PathVariable("memberId") Long memberId) {
//        return memberService.findOne(memberId);
//    }
//
//    @PostMapping("/members/{memberId}")
//    public MemberResponseDto updateMember(
//            @PathVariable("memberId") Long memberId,
//            @RequestBody MemberUpdateDto dto
//    ) {
//        return memberService.update(memberId, dto);
//    }
//
//    @GetMapping("/securitytest")
//    public ResponseEntity<String> test() {
//        log.info("[Controller] security");
//        return ResponseEntity.ok("token");
//    }

    @GetMapping("/signup")
    public String signup(MemberCreateForm memberCreateForm) {

        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!memberCreateForm.getPassword().equals(memberCreateForm.getCheckPassword())) {
            bindingResult.rejectValue("checkPassword", "passwordInCorrect",
                    "패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            memberService.join(new MemberSaveDto(
                    memberCreateForm.getUsername(),
                    memberCreateForm.getPassword()
            ));
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }
}
