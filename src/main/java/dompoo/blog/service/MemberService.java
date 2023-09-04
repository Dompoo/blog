package dompoo.blog.service;

import dompoo.blog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public String login() {

        //TODO: 유저 확인 로직 추가 필요

        return "ok";
    }
}
