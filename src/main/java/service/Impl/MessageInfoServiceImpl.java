package service.Impl;

import dao.MessageDao;
import dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.MessageInfoService;
import java.util.List;

@Service
public class MessageInfoServiceImpl implements MessageInfoService {
    private final MessageDao messageDao;

    @Autowired
    public MessageInfoServiceImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public List<Message> selectMessage() {
        List<Message> msg = messageDao.selectMessage();
        return msg;
    }
}
