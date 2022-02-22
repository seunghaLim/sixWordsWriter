package toyProject.sixWordsWriter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

//    // 좋아요 체크값 1이면 누른거 0이면 안누른거
//    private int check;

    /// 연관관계 메서드 - 좋아요 생성할 때 호출해야함
    public void setBoard(Board board){
        this.board = board;
        board.getLikes().add(this);
    }

    // 생성메서드
    public static Likes createLike(Member member, Board board){

        Likes likes = new Likes();
        likes.setMember(member);

        //연관관계 추가
        likes.setBoard(board);

        return likes;
    }







}
