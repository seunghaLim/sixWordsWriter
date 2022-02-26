package toyProject.sixWordsWriter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import toyProject.sixWordsWriter.DuplicatedIdEx;
import toyProject.sixWordsWriter.FailedLoginEx;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.repository.MemberJpaRepository;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {


    private final MemberJpaRepository memberJpaRepository;

    public Member login(String loginId, String password){

        // 중복 아이디 있어도 되는거 아님????
        Member member = memberJpaRepository.findByLoginId(loginId);

        if (member == null){
            throw new FailedLoginEx("존재하지 않는 아이디입니다");
        }

        if (member.getPassword().equals(password)){
            return member;
        } else {
            throw new FailedLoginEx("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

    }
}
