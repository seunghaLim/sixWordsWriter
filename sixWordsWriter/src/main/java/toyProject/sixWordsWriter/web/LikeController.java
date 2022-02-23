package toyProject.sixWordsWriter.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import toyProject.sixWordsWriter.domain.Board;
import toyProject.sixWordsWriter.domain.Likes;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.service.BoardService;
import toyProject.sixWordsWriter.service.LikesService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LikeController {


    private final LikesService likesService;
    private final BoardService boardService;

    @PostMapping("/like")
    @ResponseBody
    public int like(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
            @RequestParam("likeCheck") int likeCheck,
            @RequestParam("boardId") Long boardId, Model model){

        log.info("likeCheck = " + likeCheck);
        log.info("boardId = " + boardId);

        if (likeCheck == 1){
            log.info("좋아요가 눌러져 있는 상태. 취소로직 가동");
            likesService.unlike(loginMember.getId(), boardId);

        } else if (likeCheck == 0){
            log.info("좋아요가 안눌러져 있는 상태. 누르는 로직 가동");
            Likes like = likesService.like(loginMember.getId(), boardId);
            log.info("like = " + like);
        }

        Board findBoard = boardService.findByBoardId(boardId);
        int updateLikeCnt = findBoard.getLikeCount();

        return updateLikeCnt;

    }
}
