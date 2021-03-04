package dao;

public class DaoSqls {
    public static final String BOOK_SELECT = "SELECT bookNo, bookName, category , img, href, plattform FROM book_info WHERE category LIKE :category ORDER BY bookNo limit :start, :limit ";
    public static final String BOOK_SELECT_COUNT = "SELECT count(*) FROM book_info WHERE category LIKE ";
    public static final String BOOK_SELECT_SEARCH = "SELECT * FROM book_info WHERE bookName LIKE ";
    public static final String LOGIN_CHECK = "SELECT * FROM user_info WHERE userId = :userId";
    public static final String ID_CHECK = "SELECT count(*) FROM user_info WHERE userID = :userId";
    public static final String MBTI_SELECT = "SELECT m.mbtiNo, m.bookNo, b.bookName, b.category , b.img, b.href, b.plattform, m.mbtiKey FROM mbti_info AS m JOIN book_info AS b ON m.bookNO = b.bookNo WHERE m.mbtiKey = :mbtiKey ORDER BY mbtiNo limit :start, :limit";
    public static final String MBTI_SELECT_COUNT = "SELECT count(*) FROM mbti_info WHERE mbtiKey LIKE";
    public static final String MESSAGE_SELECT = "SELECT * FROM message_info ORDER BY mNo DESC";
    public static final String MESSAGE_COUNT_SELECT = "SELECT count(*) FROM message_info";
}
