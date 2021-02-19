package controller;


import dto.Mbti;
import dto.Book;
import dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.BookService;
import java.util.List;
import common.Criteria;
import service.MessageInfoService;


@Controller
public class BookController {

    private final BookService bookService;
    private final MessageInfoService messageInfoService;
    private int bookCount = 36;

    @Autowired
    public BookController(BookService bookService, MessageInfoService messageInfoService){
        this.bookService = bookService;
        this.messageInfoService = messageInfoService;
    }

    @GetMapping(path = "/list")
    public String list(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                       @RequestParam(name = "book_category", required = false) String book_category,
                       ModelMap model) {
        Criteria criteria = new Criteria(page, book_category, bookService);
        criteria.book_category();
        int startPage = criteria.getStartPage();
        int endPage = criteria.getEndPage();
        int totalPages = criteria.getTotalPages();

        //bookNo가 start로 시작하는 도서 목록 구하기
        int start = page * bookCount;
        List<Book> bookList = bookService.getRidi(start, book_category);
        List<Message> message = messageInfoService.selectMessage();

        model.addAttribute("book_category", book_category);
        model.addAttribute("list", bookList);
        //model.addAttribute("msg", message);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);

        return "list";
    }

    @GetMapping(path = "/searchResult")
    public String searchResult(@RequestParam(name = "result") String result ,ModelMap model) {
        List<Book> resultList = bookService.getRidiResult(result);
        model.addAttribute("result", resultList);

        return "searchResult";
    }
    //검색한 도서와 같은 카테고리 도서를 추천함
    @GetMapping(path = "/mbti")
    public String getMbtiList(@RequestParam(name = "mbtiKey", required = false) String mbtiKey,
                              @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                              ModelMap model){
        Criteria criteria = new Criteria(page, mbtiKey, bookService);
        criteria.mbti_category();
        int startPage = criteria.getStartPage();
        int endPage = criteria.getEndPage();
        int totalPages = criteria.getTotalPages();
        // bookNo가 start로 시작하는 도서 목록 구하기
        int start = page * bookCount;

        List<Mbti> mbtiList = bookService.getMbti(start, mbtiKey);
        model.addAttribute("mbtiList", mbtiList);
        model.addAttribute("mbtiKey", mbtiKey);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);
        return "mbti";
    }
}

