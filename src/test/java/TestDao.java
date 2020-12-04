import dao.BookDao;
import dto.Mbti;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class TestDao {
    @Autowired
    BookDao bookDao;
    Mbti mbti;

    @Before
    public void setUp(){
        mbti = new Mbti();
        mbti.setBookNo(2);
        mbti.setMbtiKey("INTJ");
        mbti.setMbtiNo(3);
        mbti.setBookName("안녕");
        mbti.setCategory("인문");
        mbti.setHref("dfdf");
        mbti.setImg("dfdff");
        mbti.setPlattform("리디");
    }

    @Test
    @Transactional
    public void testRidiDao() throws Exception{
        bookDao.selectAll(1,10,"INTJ");
    }
}
