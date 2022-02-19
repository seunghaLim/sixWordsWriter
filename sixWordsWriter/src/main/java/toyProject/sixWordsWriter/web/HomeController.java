package toyProject.sixWordsWriter.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyProject.sixWordsWriter.domain.Board;
import toyProject.sixWordsWriter.service.BoardService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;

    @GetMapping("/")
    public String home(Model model){

        List<Board> orderedByL = boardService.findByLikesCnt();
        List<Board> orderedByD = boardService.findByLatestDate();
        model.addAttribute("orderedByL", orderedByL);
        model.addAttribute("orderedByD", orderedByD);

        return "home";
    }


}
