package toyProject.sixWordsWriter.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyProject.sixWordsWriter.domain.Board;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.repository.LikesJpaRepository;
import toyProject.sixWordsWriter.service.BoardService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;
    private final LikesJpaRepository likesJpaRepository;

    @GetMapping("/")
    public String home(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                       Model model){

        List<Board> orderedByL = boardService.findByLikesCnt();
        List<Board> orderedByD = boardService.findByLatestDate();
        model.addAttribute("orderedByL", orderedByL);
        model.addAttribute("orderedByD", orderedByD);

        if(loginMember == null){
            return "home";
        }

        List<Long> wholikesBoard = getLikeBoardId(loginMember, orderedByL);

        model.addAttribute("memberId", loginMember.getId());
        model.addAttribute("whoLikesBoard", wholikesBoard);

        return "loginHome";
    }

    private List<Long> getLikeBoardId(Member loginMember, List<Board> orderedByL) {
        List<Board> likesBoard = boardService.findLikesBoard(loginMember.getId()); // 이걸 어떻게 쓸까....
        List<Long> whoLikesBoard = new ArrayList<>();

        for (Board board : orderedByL) {
            for (Board likeBoard : likesBoard) {
                if (board.getId() == likeBoard.getId()){
                    whoLikesBoard.add(board.getId());
                }
            }
        }
        return whoLikesBoard;
    }


}
