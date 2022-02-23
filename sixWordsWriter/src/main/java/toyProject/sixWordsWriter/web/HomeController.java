package toyProject.sixWordsWriter.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyProject.sixWordsWriter.domain.Board;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.domain.Pagination;
import toyProject.sixWordsWriter.repository.BoardJpaRepository;
import toyProject.sixWordsWriter.repository.LikesJpaRepository;
import toyProject.sixWordsWriter.service.BoardService;
import toyProject.sixWordsWriter.service.LikesService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final BoardService boardService;
    private final LikesService likesService;
    private final BoardJpaRepository boardJpaRepository;

    @GetMapping("/")
    public String home(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                       Model model,
                       @RequestParam(defaultValue = "1") int page){

        int totalListCnt = boardJpaRepository.findAllCnt();
        Pagination pagination = new Pagination(totalListCnt, page);
        int startIndex = pagination.getStartIndex();
        int pageSize = pagination.getPageSize();

        List<Board> orderedByL = boardService.findByLikesCnt(startIndex, pageSize);
        List<Board> orderedByD = boardService.findByLatestDate(startIndex, pageSize);

        model.addAttribute("orderedByL", orderedByL);
        model.addAttribute("orderedByD", orderedByD);
        model.addAttribute("pagination", pagination);

        if(loginMember == null){
            return "home";
        }

        Map<Long, Integer> myLikeBoardId = likesService.getLikeBoardId(loginMember.getId(), orderedByL);

        model.addAttribute("memberId", loginMember.getId());
        model.addAttribute("myLikeBoardId", myLikeBoardId);

        return "loginHome";
    }




}
