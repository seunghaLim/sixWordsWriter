package toyProject.sixWordsWriter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.repository.MemberJpaRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberJpaRepository memberJpaRepository;

    public Member login(String loginId, String password){

        // 중복 아이디 있어도 되는거 아님????
        Member member = memberJpaRepository.findByLoginId(loginId);
        if (member.getPassword().equals(password)){
            return member;
        } else {
            log.info("비번이 다름");
            return null;
        }

    }
}
