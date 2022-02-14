package toyProject.sixWordsWriter.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter
@Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String name;
    private Long loginId;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
