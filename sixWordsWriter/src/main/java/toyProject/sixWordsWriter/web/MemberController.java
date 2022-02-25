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
import toyProject.sixWordsWriter.DuplicatedIdEx;
import toyProject.sixWordsWriter.NoAuthorizationEx;
import toyProject.sixWordsWriter.SessionConst;
import toyProject.sixWordsWriter.domain.Board;
import toyProject.sixWordsWriter.domain.Pagination;
import toyProject.sixWordsWriter.domain.Role;
import toyProject.sixWordsWriter.dto.MemberDto;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.service.BoardService;
import toyProject.sixWordsWriter.service.LikesService;
import toyProject.sixWordsWriter.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final LikesService likesService;

    @ExceptionHandler(DuplicatedIdEx.class)
    public String HandlerException(Exception ex, HttpServletRequest request, Model model) {

        model.addAttribute("exMessage", ex.getMessage());
        model.addAttribute("redirectURI", request.getRequestURI());

        log.info("exMessage = " + ex.getMessage());
        log.info("redirectURI = " + request.getRequestURI());

        return "error/DuplicatedIdEx-redirect";
    }

    @GetMapping("/member/new")
    public String createJoinForm(Model model){

        model.addAttribute("member", new Member());
        return "member/new";
    }

    @PostMapping("/member/new")
    public String join(@Validated @ModelAttribute("member") MemberDto dto, BindingResult bindingResult,
                       Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            log.info("binding Error = " + bindingResult);
            return "member/new";
        }

        Member member = new Member();
        member.setLoginId(dto.getLoginId());
        member.setName(dto.getName());
        member.setPassword(dto.getPassword());
        member.setRole(Role.ADMIN);

        memberService.join(member);

        // 로그인 처리
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

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
        Map<Long, Integer> myLikeBoardId = likesService.getLikeBoardId(memberId, writeBoards);

        model.addAttribute("writeBoards", writeBoards);
        model.addAttribute("likeBoards", likesBoard);
        model.addAttribute("myLikeBoardId", myLikeBoardId);
        model.addAttribute("memberId", memberId);

        return "member/mypage";

    }







}
