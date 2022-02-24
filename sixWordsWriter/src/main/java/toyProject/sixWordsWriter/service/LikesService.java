package toyProject.sixWordsWriter.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import toyProject.sixWordsWriter.domain.Board;
import toyProject.sixWordsWriter.domain.Likes;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.repository.BoardJpaRepository;
import toyProject.sixWordsWriter.repository.LikesJpaRepository;
import toyProject.sixWordsWriter.repository.MemberJpaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class LikesService {

    private final LikesJpaRepository likesJpaRepository;
    private final BoardJpaRepository boardJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    // 좋아요 등록 (회원 id와 게시글 id를 받아서 새로운 좋아요 객체 만든 다음에 persist) - 좋아요는 한 게시물당 한번만
    public Likes like(Long memberId, Long boardId){
        
        // like 생성해서 저장
        Member findMember = memberJpaRepository.findById(memberId);
        Board findBoard = boardJpaRepository.findByBoardId(boardId);

        // 해당 member가 해당 게시글에 좋아요를 누른 적 있는지 확인
        if (isNotAlreadyLike(findMember, findBoard)) {

            Likes like = Likes.createLike(findMember, findBoard);
            Likes newLike = likesJpaRepository.like(like);
            findBoard.addLikeCount();

            return newLike;

        } else {
            throw new IllegalStateException("이미 좋아요 한 게시글입니다");
        }

    }

    // 해당 게시글 좋아요 -1하고 좋아요 취소 - 비회원은 취소 어떻게???
    public void unlike(Long memberId, Long boardId){

        Board findBoard = boardJpaRepository.findByBoardId(boardId);
        likesJpaRepository.unlike(memberId, boardId, findBoard);
        findBoard.minusLikeCount();


    }

    private boolean isNotAlreadyLike(Member findMember, Board findBoard) {
        Likes findLike = likesJpaRepository.findLike(findMember, findBoard);

        if (findLike == null){
            return true;
        } else {
            return false;
        }

    }

    // 보드 아이디가 키, 좋아요 눌렀는지 안눌렀는지가 값  가져오기
    public Map<Long, Integer> getLikeBoardId(Long memberId, List<Board> boards) {
        List<Board> likesBoard = boardJpaRepository.findLikesBoard(memberId);
        Map<Long, Integer> myLikesBoardId = new HashMap<>();

        for (Board board : boards) {
            for (Board likeBoard : likesBoard) {
                if (board.getId() == likeBoard.getId()){
                    myLikesBoardId.put(board.getId(), 1);
                } else {
                    myLikesBoardId.put(board.getId(), 0);
                }
            }
        }
        return myLikesBoardId;
    }



}
