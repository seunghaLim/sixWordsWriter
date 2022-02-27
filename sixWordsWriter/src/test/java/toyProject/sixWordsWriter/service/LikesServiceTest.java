package toyProject.sixWordsWriter.service;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import toyProject.sixWordsWriter.domain.Board;
import toyProject.sixWordsWriter.domain.Likes;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.domain.Role;
import toyProject.sixWordsWriter.repository.LikesJpaRepository;
import toyProject.sixWordsWriter.repository.MemberJpaRepository;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LikesServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    BoardService boardService;
    @Autowired
    LikesService likesService;
    @Autowired
    LikesJpaRepository likesJpaRepository;


    @Test
    public void 좋아요() {

        Member member = getMember("test2", "test2", "테스트", Role.ADMIN);
        memberService.join(member);

        Board board = getBoard(member, "테 스 트 입 니 다");
        boardService.save(board);

        Likes like = likesService.like(member.getId(), board.getId());

        Assertions.assertThat(board.getLikeCount()).isEqualTo(1);
        Assert.assertEquals("좋아요 수 확인", 1, board.getLikes().size());
        Assert.assertEquals("좋아요 객체 확인", like, board.getLikes().get(0));

        System.out.println("좋아요한 객체 = " + like);
        System.out.println("좋아요 리스트 = " + board.getLikes());
        
    }

    @Test
    public void 좋아요취소() {

        Member member = getMember("test3", "test3", "테스트", Role.ADMIN);
        Member member2 = getMember("test2", "test2", "테스트", Role.ADMIN);

        memberService.join(member);
        memberService.join(member2);


        Board board = getBoard(member, "테 스 트 입 니 다");
        boardService.save(board);

        Likes like1 =likesService.like(member.getId(), board.getId());
        Likes like2 = likesService.like(member2.getId(), board.getId());
        likesService.unlike(member.getId(), board.getId());

        Assertions.assertThat(board.getLikeCount()).isEqualTo(1);
        System.out.println("좋아요 리스트 " + board.getLikes());
        Assert.assertEquals("좋아요 1이어야함", 1, board.getLikes().size());
        Assertions.assertThat(board.getLikes().get(0)).isEqualTo(like2);

    }



    @Test(expected = IllegalStateException.class)
    public void 중복좋아요안됨(){

        Member member = getMember("test2", "test2", "테스트", Role.ADMIN);
        memberService.join(member);

        Board board = getBoard(member, "테 스 트 입 니 다");
        boardService.save(board);

        likesService.like(member.getId(), board.getId());
        likesService.like(member.getId(), board.getId());
        
        Assert.fail("중복 좋아요 시 예외 터짐");


    }


    private Board getBoard(Member member, String content) {

        Board board = new Board();
        board.setMember(member);
        board.setContent(content);

        return board;
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