package toyProject.sixWordsWriter.repository;

import org.springframework.stereotype.Repository;
import toyProject.sixWordsWriter.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberJpaRepository {

    @PersistenceContext
    EntityManager em;

    // 회원 가입
    public Member save(Member member){
        em.persist(member);
        return member;
    }

    // id로 회원 찾기 - 로그인할 때 사용
    public Member findById(Long id){
        return em.find(Member.class, id);
    }

}
