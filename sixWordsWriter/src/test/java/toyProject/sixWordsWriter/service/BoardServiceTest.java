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
import toyProject.sixWordsWriter.repository.BoardJpaRepository;

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
    @Autowired
    LikesService likesService;



    @Test
    public void 글조회(){

        Member member1 = getMember("test1", "test1", "test1", Role.ADMIN);
        Member member2 = getMember("test2", "test2", "test2", Role.ADMIN);

        memberService.join(member1);
        memberService.join(member2);

        Board board1 = getBoard(member1, "글내용입니다");
        Board board2 = getBoard(member2, "테스트입니다");
        Board board3 = getBoard(member1, "테스트입니다");
        Board board4 = getBoard(member2, "글내용입니다");

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
    public void 키워드로조회(){

        Member member1 = getMember("test1", "test1", "test1", Role.ADMIN);
        Member member2 = getMember("test2", "test2", "test2", Role.ADMIN);

        memberService.join(member1);
        memberService.join(member2);

        Board board1 = getBoard(member1, "글내용입니다");
        Board board2 = getBoard(member2, "테스트입니다");
        Board board3 = getBoard(member1, "테스트입니다");
        Board board4 = getBoard(member2, "글내용입니다");

        boardService.save(board1);
        boardService.save(board2);
        boardService.save(board3);
        boardService.save(board4);

        List<Board> findBoards = boardService.findByKeyword("테스트");

        for (Board findBoard : findBoards) {
            Assertions.assertThat(findBoard.getContent()).contains("테스트");
        }

    }


    @Test
    public void 좋아요탑5조회(){

        Member member1 = getMember("test1", "test2", "test2", Role.ADMIN);
        Member member2 = getMember("test2", "test2", "test2", Role.ADMIN);
        Member member3 = getMember("test3", "test2", "test2", Role.ADMIN);
        Member member4 = getMember("test4", "test2", "test2", Role.ADMIN);
        Member member5 = getMember("test5", "test2", "test2", Role.ADMIN);

        memberService.join(member1);
        memberService.join(member2);
        memberService.join(member3);
        memberService.join(member4);
        memberService.join(member5);

        Board board1 = getBoard(member1, "테스트1입니다");
        Board board2 = getBoard(member2, "테스트2입니다");
        Board board3 = getBoard(member1, "테스트3입니다");
        Board board4 = getBoard(member2, "테스트4입니다");
        Board board5 = getBoard(member2, "테스트5입니다");
        Board board6 = getBoard(member2, "테스트6입니다");

        boardService.save(board1);
        boardService.save(board2);
        boardService.save(board3);
        boardService.save(board4);
        boardService.save(board5);
        boardService.save(board6);


        likesService.like(member1.getId(), board3.getId());
        likesService.like(member2.getId(), board3.getId());
        likesService.like(member3.getId(), board3.getId());
        likesService.like(member4.getId(), board3.getId());
        likesService.like(member5.getId(), board3.getId());

        likesService.like(member1.getId(), board2.getId());
        likesService.like(member2.getId(), board2.getId());
        likesService.like(member3.getId(), board2.getId());
        likesService.like(member4.getId(), board2.getId());

        likesService.like(member1.getId(), board1.getId());
        likesService.like(member2.getId(), board1.getId());
        likesService.like(member3.getId(), board1.getId());


        List<Board> top5likes = boardService.top5Likes();

        int i = 1;
        for (Board top5like : top5likes) {
            System.out.println(i + "위");
            System.out.println("게시글 내용 " + top5like.getContent());
            System.out.println("게시글 작성자 로그인id " + top5like.getMember().getLoginId());
            System.out.println("게시글 좋아요 수 " + top5like.getLikeCount());
            System.out.println("\n");
            i = i+1;
        }
        // 3, 2, 1, 5, 4 순으로 출력되어야 함


    }

    @Test
    public void 글삭제(){

        Member member1 = getMember("test1", "test2", "test2", Role.ADMIN);
        memberService.join(member1);

        Board board1 = getBoard(member1, "테스트1입니다");
        boardService.save(board1);
        likesService.like(member1.getId(), board1.getId());

        boardService.delete(board1.getId());

        // 글 삭제할 때 해당 글에 누른 좋아요는 어떻게 처리함??? - 일단 cascade로 했음

        Board findBoard = boardService.findByBoardId(board1.getId());

        Assertions.assertThat(findBoard).isNull();


    }

    @Test
    public void 내가쓴글찾기 (){

        Member member1 = getMember("test1", "test2", "test2", Role.ADMIN);
        memberService.join(member1);

        Board board1 = getBoard(member1, "테스트1입니다");
        boardService.save(board1);

        List<Board> findBoards = boardService.findByMemberId(member1.getId());

        Assertions.assertThat(findBoards.size()).isEqualTo(1);
        Assertions.assertThat(findBoards.get(0)).isEqualTo(board1);


    }



    @Test
    //@Rollback(false)
    public void 내가누른좋아요글확인(){

        Member member1 = getMember("test1", "test2", "test2", Role.ADMIN);
        memberService.join(member1);

        Board board1 = getBoard(member1, "테스트1입니다");
        Board board2 = getBoard(member1, "테스트2입니다");
        Board board3 = getBoard(member1, "테스트3입니다");

        boardService.save(board1);
        boardService.save(board2);
        boardService.save(board3);

        likesService.like(member1.getId(), board1.getId());
        likesService.like(member1.getId(), board3.getId());

        List<Board> likesBoard = boardService.findLikesBoard(member1.getId());

        for (Board board : likesBoard) {
            System.out.println("좋아요한 게시글 내용 = " + board.getContent() );
        }

        Assertions.assertThat(likesBoard.size()).isEqualTo(2);

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