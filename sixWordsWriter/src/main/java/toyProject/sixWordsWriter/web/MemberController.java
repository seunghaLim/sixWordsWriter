package toyProject.sixWordsWriter.web;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyProject.sixWordsWriter.domain.Board;
import toyProject.sixWordsWriter.domain.Role;
import toyProject.sixWordsWriter.dto.MemberDto;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.service.BoardService;
import toyProject.sixWordsWriter.service.MemberService;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;

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

    @GetMapping("/member/{id}")
    public String createMypageForm(@PathVariable("id") Long memberId, Model model){

        List<Board> writeBoards = boardService.findByMemberId(memberId);
        List<Board> likesBoard = boardService.findLikesBoard(memberId);

        model.addAttribute("writeBoards", writeBoards);
        model.addAttribute("likeBoards", likesBoard);
        model.addAttribute("memberId", memberId);

        return "member/mypage";

    }







}
