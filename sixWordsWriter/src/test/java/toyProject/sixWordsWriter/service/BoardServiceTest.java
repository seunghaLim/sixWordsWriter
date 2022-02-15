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

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    BoardService boardService;
    @Autowired
    MemberService memberService;


    @Test
    public void 글조회(){

        Member member1 = getMember("test1", "test1", "test1", Role.ADMIN);
        Member member2 = getMember("test2", "test2", "test2", Role.ADMIN);

        memberService.join(member1);
        memberService.join(member2);

        Board board1 = getBoard(member1, "글내용입니다", null);
        Board board2 = getBoard(member2, "테스트입니다", null);
        Board board3 = getBoard(member1, "테스트입니다", null);
        Board board4 = getBoard(member2, "글내용입니다", null);

        boardService.save(board1);
        boardService.save(board2);
        boardService.save(board3);
        boardService.save(board4);

        List<Board> findBoards = boardService.findByName("test1");

        for (Board findBoard : findBoards) {
            Assertions.assertThat(findBoard.getMember().getName()).isEqualTo("test1");
        }

    }

    @Test
    public void 컨텐츠로조회(){

        Member member1 = getMember("test1", "test1", "test1", Role.ADMIN);
        Member member2 = getMember("test2", "test2", "test2", Role.ADMIN);

        memberService.join(member1);
        memberService.join(member2);

        Board board1 = getBoard(member1, "글내용입니다", null);
        Board board2 = getBoard(member2, "테스트입니다", null);
        Board board3 = getBoard(member1, "테스트입니다", null);
        Board board4 = getBoard(member2, "글내용입니다", null);

        boardService.save(board1);
        boardService.save(board2);
        boardService.save(board3);
        boardService.save(board4);

        List<Board> findBoards = boardService.findByKeyword("테스트");

        for (Board findBoard : findBoards) {
            Assertions.assertThat(findBoard.getContent()).contains("테스트");
        }

    }

    private Board getBoard(Member member, String content, Likes like) {

        Board board = new Board();
        board.setMember(member);
        board.setContent(content);
        board.getLikes().add(like);

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