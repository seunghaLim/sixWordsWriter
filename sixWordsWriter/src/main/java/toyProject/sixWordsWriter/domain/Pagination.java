package toyProject.sixWordsWriter.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Pagination {

    private int pageSize;
    private int blockSize;
    private int page = 1;
    private int block = 1;
    private int totalListCnt;
    private int totalPageCnt;
    private int totalBlockCnt;
    private int startPage = 1;
    private int endPage = 1;
    private int startIndex = 0;
    private int prevBlock;
    private int nextBlock;

    // 총 게시물 수와 현재 페이지를 Controller로부터 받아온다.
    public Pagination(int totalListCnt, int page) {

        /** 현재 페이지 **/
        setPage(page);

        /** 총 게시글 수 **/
        setTotalListCnt(totalListCnt);

        /** 총 페이지 수 **/
        setTotalPageCnt((int) Math.ceil(totalListCnt * 1.0 / pageSize));

        /** 총 블럭 수 **/
        setTotalBlockCnt((int) Math.ceil(totalPageCnt * 1.0 / blockSize));

        /** 현재 블럭 **/
        setBlock((int) Math.ceil((page * 1.0)/blockSize));

        /** 블럭 시작 페이지 **/
        setStartPage((block - 1) * blockSize + 1);

        /** 블럭 마지막 페이지 **/
        setEndPage(startPage + blockSize - 1);

        /* === 블럭 마지막 페이지에 대한 validation ===*/
        if(endPage > totalPageCnt){this.endPage = totalPageCnt;}

        /** 이전 블럭(클릭 시, 이전 블럭 마지막 페이지) **/
        setPrevBlock((block * blockSize) - blockSize);

        /* === 이전 블럭에 대한 validation === */
        if(prevBlock < 1) {this.prevBlock = 1;}

        /** 다음 블럭(클릭 시, 다음 블럭 첫번째 페이지) **/
        setNextBlock((block * blockSize) + 1);

        /* === 다음 블럭에 대한 validation ===*/
        if(nextBlock > totalPageCnt) {nextBlock = totalPageCnt;}

        /** DB 접근 시작 index **/
        setStartIndex((page-1) * pageSize);

    }
}
