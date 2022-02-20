package toyProject.sixWordsWriter.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyProject.sixWordsWriter.domain.Role;
import toyProject.sixWordsWriter.dto.MemberDto;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.service.MemberService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/new")
    public String createJoinForm(Model model){

        model.addAttribute("member", new Member());
        return "member/new";
    }

    @PostMapping("/member/new")
    public String join(@Validated @ModelAttribute("member") MemberDto dto, BindingResult bindingResult,
                       Model model, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            log.info("binding Error = " + bindingResult);
            return "redirect:/member/new";
        }

        Member member = new Member();
        member.setLoginId(dto.getLoginId());
        member.setName(dto.getName());
        member.setPassword(dto.getPassword());
        member.setRole(Role.ADMIN); // 나중에 운영자 계정과 구분하는거 추가

        memberService.join(member);

        redirectAttributes.addAttribute("memberName", member.getName());
        model.addAttribute("member", member);

        return "redirect:/member/afternew/{memberName}";

    }

    @GetMapping("/member/afternew/{memberName}")
    public String afterJoin(@PathVariable("memberName") String memberName, Model model){
        model.addAttribute("memberName", memberName);
        return "member/afternew";
    }






}
