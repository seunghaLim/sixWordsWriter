package toyProject.sixWordsWriter.repository;

import org.springframework.stereotype.Repository;
import toyProject.sixWordsWriter.domain.Board;
import toyProject.sixWordsWriter.domain.Likes;
import toyProject.sixWordsWriter.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class LikesJpaRepository {

    @PersistenceContext
    EntityManager em;
    
    // 좋아요 등록 (회원 id와 게시글 id를 받아서 새로운 좋아요 객체 만든 다음에 persist)
    public Likes like(Likes likes){
        em.persist(likes);
        return likes;
    }

    // 좋아요 취소 (회원 id와 게시글 id를 받아서 좋아요 찾고 취소 - 비회원 취소는 어떻게???)
    public void unlike(Long memberId, Long boardId, Board board){

        Likes findLikes = em.createQuery("select l from Likes l where l.member.id = :memberId and l.board.id = :boardId", Likes.class)
                .setParameter("memberId", memberId)
                .setParameter("boardId", boardId)
                .getSingleResult();

        // 게시글의 좋아요 연관 필드에서도 제거
        board.getLikes().remove(findLikes);

        em.remove(findLikes);

    }

    // 회원과 게시글 객체를 받아서 좋아요 객체 있는지 확인
    public Likes findLike(Member member, Board board){

        Likes findLike;
        try {

            findLike = em.createQuery("select l from Likes l where l.member = :member and l.board = :board", Likes.class)
                    .setParameter("member", member)
                    .setParameter("board", board)
                    .getSingleResult();

        } catch (NoResultException e){
            return null;
        }

        return findLike;


    }
}
