package dao;

public class DaoSqls {
    public static final String BOOK_SELECT = "SELECT bookNo, bookName, category , img, href, plattform FROM book_info WHERE category LIKE :category ORDER BY bookNo limit :start, :limit ";
    public static final String BOOK_SELECT_COUNT = "SELECT count(*) FROM book_info WHERE category LIKE ";
    public static final String BOOK_SELECT_SEARCH = "SELECT * FROM book_info WHERE bookName LIKE ";
    public static final String LOGIN_CHECK = "SELECT * FROM user_info WHERE userId = :userId";
    public static final String ID_CHECK = "SELECT count(*) FROM user_info WHERE userID = :userId";
    public static final String MBTI_SELECT = "SELECT m.mbtiNo, m.bookNo, b.bookName, b.category , b.img, b.href, b.plattform, m.mbtiKey FROM mbti_info AS m JOIN book_info AS b ON m.bookNO = b.bookNo WHERE m.mbtiKey = :mbtiKey ORDER BY mbtiNo limit :start, :limit";
    public static final String MBTI_SELECT_COUNT = "SELECT count(*) FROM mbti_info WHERE mbtiKey LIKE";
    public static final String SELECT_BOOK_GROP_CATEGORY = "SELECT bookName, category FROM(\n" +
            "\tSELECT book_info.*,\n" +
            "\t\t(CASE @groupId WHEN book_info.category THEN @rownum:=@rownum+1 ELSE @rownum:=1 END) AS rnum,\n" +
            "\t\t(@groupId:=book_info.category) groupId\n" +
            "\tFROM book_info\n" +
            "\tORDER BY category, bookNo\n" +
            ") AS LOG\n" +
            "WHERE rnum <=2";
}
