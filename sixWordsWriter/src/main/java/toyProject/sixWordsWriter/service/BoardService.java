package toyProject.sixWordsWriter.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyProject.sixWordsWriter.domain.Board;
import toyProject.sixWordsWriter.domain.Likes;
import toyProject.sixWordsWriter.repository.BoardJpaRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class BoardService {

    private final BoardJpaRepository boardJpaRepository;

    // 글 작성 및 수정
    @Transactional
    public void save(Board board){
        boardJpaRepository.save(board);
    }

    // member id로 글 조회 - 내가쓴 글 찾기
    public List<Board> findByMemberId(Long memberId){
        return boardJpaRepository.findByMemberId(memberId);
    }

    // board id로 글 조회 - 없어도 될지도
    public Board findByBoardId(Long boardId){
        return boardJpaRepository.findByBoardId(boardId);
    }

    // 작성자 이름으로 글 조회 - 검색
    public List<Board> findByName(String name){
        return boardJpaRepository.findByName(name);
    }

    // 키워드로 글 조회 - 검색 근데 만약에 검색 띄어쓰기 있으면 어떻게함??
    public List<Board> findByKeyword(String keyword){
        return boardJpaRepository.findByKeyword(keyword);
    }

    // 좋아요 높은 순 5개 조회 (페이징으로)
    public List<Board> top5Likes(){
        return boardJpaRepository.top5Likes();
    }

    // 글 삭제
    @Transactional
    public void delete(Long id){
        boardJpaRepository.delete(id);
    }

    // 내가 좋아요 누른 글 조회
    public List<Board> findLikesBoard(Long memberId){
        return boardJpaRepository.findLikesBoard(memberId);
    }


}
