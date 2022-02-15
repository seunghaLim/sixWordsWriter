package toyProject.sixWordsWriter.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyProject.sixWordsWriter.domain.Board;
import toyProject.sixWordsWriter.domain.Likes;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.repository.BoardJpaRepository;
import toyProject.sixWordsWriter.repository.LikesJpaRepository;
import toyProject.sixWordsWriter.repository.MemberJpaRepository;

@Service
@Transactional
@AllArgsConstructor
public class LikesService {

    private final LikesJpaRepository likesJpaRepository;
    private final BoardJpaRepository boardJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    // 좋아요 등록 (회원 id와 게시글 id를 받아서 새로운 좋아요 객체 만든 다음에 persist)
    public Likes like(Long memberId, Long boardId){

        // like 생성해서 저장
        Member findMember = memberJpaRepository.findById(memberId);
        Board findBoard = boardJpaRepository.findById(boardId);

        Likes likes = Likes.createLike(findMember, findBoard);
        likesJpaRepository.like(likes);
        findBoard.addLikeCount();

        return likes;

    }

    // 해당 게시글 좋아요 -1하고 좋아요 취소 - 비회원은 취소 어떻게???
    public void unlike(Long memberId, Long boardId){

        Board findBoard = boardJpaRepository.findById(boardId);
        findBoard.minusLikeCount();
        likesJpaRepository.unlike(memberId, boardId);

    }

}
