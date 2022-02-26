package toyProject.sixWordsWriter.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import toyProject.sixWordsWriter.FailedLoginEx;
import toyProject.sixWordsWriter.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Slf4j
@Repository
public class MemberJpaRepository {

    @PersistenceContext
    EntityManager em;

    // 회원 가입
    public Member save(Member member){
        em.persist(member);
        return member;
    }

    // id로 회원 찾기
    public Member findById(Long id){
        return em.find(Member.class, id);
    }

    // 로그인 id로 회원찾기
    public Member findByLoginId(String loginId){

        Member findMember;
        try {
            findMember = em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                    .setParameter("loginId", loginId)
                    .getSingleResult();

        } catch (NoResultException e){

            return null;

        }
        return findMember;
    }

}
