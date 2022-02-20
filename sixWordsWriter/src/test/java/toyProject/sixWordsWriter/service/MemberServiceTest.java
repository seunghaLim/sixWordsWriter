package toyProject.sixWordsWriter.service;

import com.jayway.jsonpath.internal.function.latebinding.ILateBindingValue;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.domain.Role;
import toyProject.sixWordsWriter.repository.MemberJpaRepository;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    LoginService loginService;
    @Test
    public void 회원가입() {

        // given
        Member member = new Member();
        member.setLoginId("tmdgk4902");
        member.setPassword("2b48r755!");
        member.setName("ddd");
        member.setRole(Role.USER);

        // when
        Long findMemberId = memberService.join(member);

        // then
        Assertions.assertEquals(member, memberJpaRepository.findById(findMemberId));


    }

    @Test
    public void 조회() {

        // given
        Member member = getMember("test", "test!", "name", Role.ADMIN);

        // when
        Long findMemberId = memberService.join(member);

        // then
        Member findMember = memberJpaRepository.findById(findMemberId);
        System.out.println("로그인아이디 = {}" + findMember.getLoginId());
        System.out.println("이름 = {}" + findMember.getName());


    }


    @Test(expected = IllegalStateException.class)
    public void 동일아이디예외() {

        // given
        Member member1 = getMember("test1", "test1", "test1", Role.ADMIN);
        Member member2 = getMember("test1", "test1", "test1", Role.ADMIN);

        // when
        memberService.join(member1);
        memberService.join(member2);

        // then
        org.assertj.core.api.Assertions.fail("예외가 발생해야 한다");

    }

    @Test
    public void 로그인(){
        Member member1 = getMember("test1", "test1", "test1", Role.ADMIN);
        memberService.join(member1);




    }



    private Member getMember(String loginId, String password, String name, Role role) {
        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(password);
        member.setName(name);
        member.setRole(role);
        return member;
    }
}