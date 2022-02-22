package toyProject.sixWordsWriter.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import toyProject.sixWordsWriter.domain.Board;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BoardJpaRepository {

    @PersistenceContext
    EntityManager em;

    // 글 작성 및 수정
    public void save(Board board){
        if (board.getId() == null){
            em.persist(board); // 새로운 객체를 저장
        }
        else {
            em.merge(board); // 이미 저장되어 있던 객체를 업데이트
        }
    }

    // 게시글 id로 게시글 찾기
    public Board findByBoardId(Long boardId) {
        return em.find(Board.class, boardId);
    }

    // member id로 글 조회 - 내가쓴 글 찾기
    public List<Board> findByMemberId(Long memberId){
        return em.createQuery("select b from Board b where b.member.id = :memberId", Board.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    // 작성자 이름으로 글 조회 -
    public List<Board> findByName(String name){
        return em.createQuery("select b from Board b where b.member.name = :name", Board.class)
                .setParameter("name", name)
                .getResultList();

    }

    // 키워드로 글 조회 - 검색 근데 만약에 검색 띄어쓰기 있으면 어떻게함??
    public List<Board> findByKeyword(String keyword){
        return em.createQuery("select b from Board b where b.content like '%" + keyword + "%'", Board.class)
                .getResultList();
    }


    // 좋아요 높은 순으로 조회
    public List<Board> findByLikesCnt(){
        return em.createQuery("select b from Board b order by b.likeCount desc", Board.class)
                .getResultList();
    }

    // 최신 순으로 조회
    public List<Board> findByLatestDate(){

        return em.createQuery("select b from Board b order by b.writeDate desc", Board.class)
                .getResultList();
    }

    // 글 삭제
    public void delete(Long id){
        Board findBoard= em.find(Board.class, id);
        // 좋아요도 삭제해야함
        em.remove(findBoard);
    }


    // 내가 좋아요 누른 글 조회
    public List<Board> findLikesBoard (Long memberId){

        return em.createQuery("select l.board from Likes l where l.member.id = :memberId", Board.class)
                .setParameter("memberId", memberId)
                .getResultList();

    }

    // 페이징 전용 메소드
    public List<Board> findListPaging(int startIndex, int pageSize){
        return em.createQuery("select b from Board b", Board.class)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .getResultList();
    }

    // 전체 글 수
    public int findAllCnt(){
        return ((Number) em.createQuery("select count(b) from Board b")
                .getSingleResult()).intValue();
    }


    // 작성자 이름으로 조회된 글 수
    public int findByNameCnt(String name){
        return ((Number) em.createQuery("select count(b) from Board b where b.member.name = :name")
                .setParameter("name", name)
                .getSingleResult()).intValue();

    }

    // 키워드로 조회된 글 수
    public int findByKeywordCnt(String keyword){
        return ((Number) em.createQuery("select count(b) from Board b where b.content like '%" + keyword + "%'")
                .getSingleResult()).intValue();
    }






}
