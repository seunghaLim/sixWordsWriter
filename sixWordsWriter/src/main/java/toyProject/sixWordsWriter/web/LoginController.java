package toyProject.sixWordsWriter.web;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toyProject.sixWordsWriter.FailedLoginEx;
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

    @ExceptionHandler(FailedLoginEx.class)
    public String HandlerException(Exception ex, HttpServletRequest request, Model model) {

        model.addAttribute("exMessage", ex.getMessage());
        model.addAttribute("redirectURI", request.getRequestURI());

        log.info("exMessage = " + ex.getMessage());
        log.info("redirectURI = " + request.getRequestURI());

        return "error/DuplicatedIdEx-redirect";
    }

    @GetMapping("/member/login")
    public String createLoginForm(@ModelAttribute("loginForm") LoginDto form){
        // @ModelAttribute 쓰면 자동으로 해당 객체를 addAttribute해서 해줌
        return "member/login";
    }

    @PostMapping("/member/login")
    public String loginForm(@Validated @ModelAttribute("loginForm") LoginDto dto, BindingResult bindingResult,
                            @RequestParam(name="redirectURL", defaultValue = "/") String redirectURL,
                            HttpServletRequest request){

        if (bindingResult.hasErrors()){
            log.info("bindingResult = " + bindingResult);
            return "member/login";
        }

        log.info("id = " + dto.getLoginId());
        log.info("password = " + dto.getPassword());

        Member loginMember = loginService.login(dto.getLoginId(), dto.getPassword());

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:" + redirectURL;
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
