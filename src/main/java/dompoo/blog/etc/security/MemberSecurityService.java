package dompoo.blog.etc.security;

import dompoo.blog.domain.Member;
import dompoo.blog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> _siteMember = memberRepository.findByUsername(username);
        if (_siteMember.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        Member member = _siteMember.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new User(member.getUsername(), member.getPassword(), authorities);
    }

    //TODO : 어댑터를 반환해서 어댑터 안의 유저DTO를 반환하도록
}
