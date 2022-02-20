package toyProject.sixWordsWriter.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyProject.sixWordsWriter.SessionConst;
import toyProject.sixWordsWriter.domain.Board;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.dto.BoardDto;
import toyProject.sixWordsWriter.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/new")
    public String createWriteForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                                  Model model){

        if (loginMember == null){
            log.info("비회원 글쓰기 불가");
            return "redirect:/member/login";
        }

        model.addAttribute("board", new Board());
        return "board/save";

    }

    @PostMapping("/board/new")
    public String write(@Validated @ModelAttribute("board") BoardDto dto, BindingResult bindingResult,
                        @SessionAttribute(name = "loginMember", required = false) Member loginMember,
                        RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            log.info("binding error = " + bindingResult);
            return "board/save";
        }

        Board board = new Board();
        board.setMember(loginMember);
        board.setContent(dto.getContent());
        board.setWriteDate(LocalDateTime.now());

        boardService.save(board);

        redirectAttributes.addAttribute("boardId", board.getId());
        return "redirect:/board/{boardId}";

    }

    @GetMapping("/board/{boardId}")
    public String board(@PathVariable("boardId") Long boardId, Model model){

        Board findBoard = boardService.findByBoardId(boardId);

        if(findBoard == null){
            throw new IllegalStateException("글을 찾을 수 없습니다");
        }

        model.addAttribute("board", findBoard);

        return "board/board";
    }
}