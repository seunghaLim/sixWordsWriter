package toyProject.sixWordsWriter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyProject.sixWordsWriter.domain.Member;
import toyProject.sixWordsWriter.repository.MemberJpaRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;

    // 회원 가입 - 회원 이름이 중복되는 경우 가입 불가
    @Transactional
    public Long join(Member member){

        UniqueLoginId(member.getLoginId());
        memberJpaRepository.save(member);

        return member.getId();
    }


    // id로 회원 찾기 - 로그인할 때 사용
    public Member findById(Long id){
        return memberJpaRepository.findById(id); // 없으면 널 반환
    }


    private void UniqueLoginId(String id) {
        Member findMember = memberJpaRepository.findByLoginId(id);

        if (findMember !=null){
            throw new IllegalStateException("이미 존재하는 id 입니다");
        }
    }
}
