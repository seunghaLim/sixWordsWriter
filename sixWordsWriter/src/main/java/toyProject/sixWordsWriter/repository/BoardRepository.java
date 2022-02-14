package toyProject.sixWordsWriter.repository;

import org.springframework.stereotype.Repository;
import toyProject.sixWordsWriter.domain.Board;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BoardRepository {

    @PersistenceContext
    EntityManager em;

    // 글 작성 및 수정
    public void write(Board board){
        if (board.getId() == null){
            em.persist(board); // 새로운 객체를 저장
        }
        else {
            em.merge(board); // 이미 저장되어 있던 객체를 업데이트
        }
    }

    // id로 글 조회 - 내가쓴 글 찾기
    public Board findById(Long id){
        return em.find(Board.class, id); // 못찾으면 null 반환
    }

    // 작성자 이름으로 글 조회 - 검색
    public List<Board> findByName(String name){
        return em.createQuery("select b from Board b where b.member.name = :name", Board.class)
                .setParameter("name", name)
                .getResultList();

    }

    // 키워드로 글 조회 - 검색 근데 만약에 검색 띄어쓰기 있으면 어떻게함??
    public List<Board> findByKeyword(String keyword){
        return em.createQuery("select b from Board b where b.content = :keyword", Board.class)
                .setParameter("keyword", keyword)
                .getResultList();
    }


    // 좋아요 높은 순 5개 조회 (페이징으로)

    // 글 삭제

    // 내가 좋아요 누른 글은 어떻게 조회함??


}
