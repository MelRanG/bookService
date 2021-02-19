package service;

import dto.Mbti;
import dto.Book;

import java.util.List;


public interface BookService {
    public static final Integer LIMIT = 36;
    public List<Book> getRidi(Integer start, String category);
    public int getCount(String category);
    public List<Book> getRidiResult(String result);
    public List<Mbti> getMbti(Integer start, String mbtiKey);
    public int getMbtiCount(String mbtiKey);
    public List<Book> getCategoryGroup();
}
