import config.DBConfig;
import dao.MessageDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import service.Impl.MessageInfoServiceImpl;


public class messageInfoServiceTest {
    MessageDao messageDao;
    DBConfig dbConfig;
    MessageInfoServiceImpl messageInfoService;

    @Before
    public void setUp(){
        dbConfig = new DBConfig();
        messageDao = new MessageDao(dbConfig.dataSource());
        messageInfoService = new MessageInfoServiceImpl(messageDao);
    }

    @Test
    @Transactional
    public void serviceTest() throws Exception{

        String msg = "메시지";
        messageInfoService.insertMessage(msg);
    }


}
