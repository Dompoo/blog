package dompoo.blog.controller;

import dompoo.blog.domain.Member;
import dompoo.blog.dto.MemberDto;
import dompoo.blog.dto.MemberSaveRequestDto;
import dompoo.blog.dto.MemberUpdataRequestDto;
import dompoo.blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class MemberController {

    private final MemberService memberService;

    //멤버 추가
    @PutMapping("/members")
    public MemberDto addMember(@RequestBody MemberSaveRequestDto dto) {
        return memberService.join(dto);
    }

    //멤버 전체 조회
    @GetMapping("/members")
    public Page<MemberDto> findMembers(@PageableDefault(size = 15) Pageable pageable) {
        return memberService.findAll(pageable);
    }

    //id로 멤버 단건 조회
    @GetMapping("/members/{memberId}")
    public MemberDto findMember(@PathVariable("memberId") Long memberId) {
        return memberService.findOne(memberId);
    }

    @PostMapping("/members/{memberId}")
    public MemberDto updateMember(
            @PathVariable("memberId") Long memberId,
            @RequestBody MemberUpdataRequestDto dto
    ) {
        return memberService.update(memberId, dto);
    }
}
