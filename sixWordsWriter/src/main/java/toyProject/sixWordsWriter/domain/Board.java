package toyProject.sixWordsWriter.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    private LocalDateTime writeDate;

    private int likeCount;

    @OneToMany(mappedBy = "board")
    private List<Likes> likes = new ArrayList<>();


    // 비즈니스 로직 - 나중에 좋아요 누르는 로직에서 사용됨
    public void addLikeCount(){
        likeCount += 1;
    }

    public void minusLikeCount(){
        likeCount -= 1;
    }

}
