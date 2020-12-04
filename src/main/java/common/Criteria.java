package common;

import org.springframework.beans.factory.annotation.Autowired;
import service.BookService;

//Criteria = 분류
public class Criteria {
    private int cPage;
    private int startPage;
    private int endPage;
    private int totalPages;
    private String book_category;

    private Integer rowNum = 36;
    private Integer blockNum = 5;

    private final BookService bookService;

    @Autowired
    public Criteria(Integer cPage, String book_category, BookService bookService){
        this.cPage = cPage;
        this.book_category = book_category;
        this.bookService = bookService;
    }

    public void book_category(){
        int count = bookService.getCount(book_category);
        Calc(count);
    }

    public void mbti_category(){
        int count = bookService.getMbtiCount(book_category);
        Calc(count);
    }
    public void Calc(int totalBooks){
        if(cPage == 0){
            cPage += 1;
        }
        //총 도서수, 페이지수를 구한다.
        totalPages = totalBooks % rowNum == 0 ? totalBooks / rowNum: (totalBooks / rowNum) + 1;
        //현재 페이지가 몇번 째 블록라인인지 구한다.
        int currentBlockBundle = cPage % blockNum == 0 ? cPage / blockNum: (cPage / blockNum) + 1;
        //그 블록라인의 시작값과 끝값을 구한다.
        startPage = (currentBlockBundle - 1) * blockNum + 1;
        endPage = startPage + blockNum - 1;

        if(endPage > totalPages){
            endPage = totalPages;
        }
    }
    public int getStartPage() {
        return startPage;
    }
    public int getEndPage(){
        return endPage;
    }
    public int getTotalPages() {
        return totalPages;
    }

}
