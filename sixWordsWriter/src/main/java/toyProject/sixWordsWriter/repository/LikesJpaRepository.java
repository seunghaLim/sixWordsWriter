package toyProject.sixWordsWriter.repository;

import org.springframework.stereotype.Repository;
import toyProject.sixWordsWriter.domain.Likes;

import javax.persistence.EntityManager;
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
    public void unlike(Long memberId, Long boardId){

        Likes findLikes = em.createQuery("select l from Likes l where l.member.id = :memberId and l.board = :boardId", Likes.class)
                .setParameter("memberId", memberId)
                .setParameter("boardId", boardId)
                .getSingleResult();

        em.remove(findLikes);

    }
}
