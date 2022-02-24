package toyProject.sixWordsWriter.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyProject.sixWordsWriter.domain.Board;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.dto.BoardDto;
import toyProject.sixWordsWriter.service.BoardService;
import toyProject.sixWordsWriter.service.LikesService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class BoardController {

    private final BoardService boardService;
    private final LikesService likesService;

    @GetMapping("/board/new")
    public String createWriteForm(Model model,
                                  @SessionAttribute(name = "loginMember", required = false) Member loginMember){

        model.addAttribute("board", new Board());
        model.addAttribute("memberId", loginMember.getId());

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

        return "redirect:/board";

    }

    @GetMapping("/board")
    public String board(@RequestParam("boardId") Long boardId,
                        @SessionAttribute(name = "loginMember", required = false) Member loginMember,
                        Model model){

        Board findBoard = boardService.findByBoardId(boardId);
        model.addAttribute("board", findBoard);
        
        if(loginMember == null){
            return "board/board";
        }

        List<Board> boards = boardService.findAll();
        Map<Long, Integer> myLikeBoardId = likesService.getLikeBoardId(loginMember.getId(), boards);

        model.addAttribute("memberId", loginMember.getId());
        model.addAttribute("myLikeBoardId", myLikeBoardId);

        return "board/loginBoard";
    }

    @GetMapping("/board/edit")
    public String createEditForm(@RequestParam("boardId") Long boardId,
                                 @SessionAttribute(name = "loginMember", required = false) Member loginMember,
                                 Model model){

        Board findBoard = boardService.findByBoardId(boardId);

        if(!isLoginMemberhteWriter(loginMember.getId(), findBoard.getMember().getId())){
            throw new IllegalStateException("수정 권한이 없습니다.");
        }

        model.addAttribute("board", findBoard);
        model.addAttribute("memberId", loginMember.getId());

        return "board/edit";
    }

    @PostMapping("/board/edit")
    public String edit(@Validated @ModelAttribute("board") BoardDto dto,
                       @RequestParam("boardId") Long boardId,
                       RedirectAttributes redirectAttributes){

        Board findBoard = boardService.findByBoardId(boardId);

        findBoard.setContent(dto.getContent());
        boardService.save(findBoard);

        redirectAttributes.addAttribute("boardId", findBoard.getId());

        return "redirect:/board";
    }

    private boolean isLoginMemberhteWriter(Long loginMemberId, Long writerId) {

        return loginMemberId.equals(writerId);
    }

    @GetMapping("/board/delete")
    public String createDeleteForm(@RequestParam("boardId") Long boardId, Model model){

        model.addAttribute("boardId", boardId);

        return "board/delete";
    }

    @PostMapping("/board/delete")
    public String delete(@RequestParam("boardId") Long boardId,
                         @SessionAttribute(name = "loginMember", required = false) Member loginMember){


        log.info("boardId " + boardId);
        log.info("삭제하려는 멤버 아이디 " + loginMember.getId());

        Board findBoard = boardService.findByBoardId(boardId);

        if( !loginMember.getId().equals(findBoard.getMember().getId()) ){
            throw new IllegalStateException("삭제할 권한이 없습니다");
        }

        boardService.delete(boardId);

        return "redirect:/"; // 삭제되었다고 모달폼이라도 띄워야 되지 않을까
    }


}