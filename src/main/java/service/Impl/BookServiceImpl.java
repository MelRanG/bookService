package service.Impl;

import dao.BookDao;
import dto.Mbti;
import dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao){
        this.bookDao = bookDao;
    }

    @Override
    @Transactional
    public List<Book> getRidi(Integer start, String category) {
        List<Book> list = bookDao.selectAll(start, BookService.LIMIT, category);
        return list;
    }
    @Override
    @Transactional
    public List<Book> getRidiResult(String result) {
        List<Book> list = bookDao.searchResult(result);
        return list;
    }
    @Override
    @Transactional
    public List<Mbti> getMbti(Integer start, String mbtiKey) {
        List<Mbti> mbti = bookDao.selectMbtiAll(start, BookService.LIMIT, mbtiKey);
        return mbti;
    }

    @Override
    public int getCount(String category) {
        return bookDao.selectCount(category);
    }

    @Override
    public int getMbtiCount(String mbtiKey) {
        return bookDao.selectMbtiCount(mbtiKey);
    }
}
