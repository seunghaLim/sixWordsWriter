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


    @ExceptionHandler(FailedLoginEx.class)
    public String HandlerException(Exception ex, HttpServletRequest request, Model model) {

        model.addAttribute("exMessage", ex.getMessage());
        model.addAttribute("redirectURI", request.getRequestURI());

        log.info("exMessage = " + ex.getMessage());
        log.info("redirectURI = " + request.getRequestURI());

        return "error/DuplicatedIdEx-redirect";
    }

    private final MemberJpaRepository memberJpaRepository;

    public Member login(String loginId, String password){

        // 중복 아이디 있어도 되는거 아님????
        Member member = memberJpaRepository.findByLoginId(loginId);
        if (member.getPassword().equals(password)){
            return member;
        } else {
            throw new FailedLoginEx("비밀번호가 일치하지 않습니다.");
        }

    }
}
