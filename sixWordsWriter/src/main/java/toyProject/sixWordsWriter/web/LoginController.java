package toyProject.sixWordsWriter.web;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import toyProject.sixWordsWriter.SessionConst;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.dto.LoginDto;
import toyProject.sixWordsWriter.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/member/login")
    public String createLoginForm(@ModelAttribute("loginForm") LoginDto form){
        // @ModelAttribute 쓰면 자동으로 해당 객체를 addAttribute해서 해줌
        return "member/login";
    }

    @PostMapping("/member/login")
    public String loginForm(@Validated @ModelAttribute("loginForm") LoginDto dto, BindingResult bindingResult,
                            HttpServletRequest request){

        if (bindingResult.hasErrors()){
            log.info("bindingResult = " + bindingResult);
            return "member/login";
        }

        log.info("id = " + dto.getLoginId());
        log.info("password = " + dto.getPassword());

        Member loginMember = loginService.login(dto.getLoginId(), dto.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
            return "member/login";
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    @GetMapping("/member/logout")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }

        return "redirect:/";
    }

}
