package service;

import dto.Message;

import java.util.List;

public interface MessageInfoService {
    public boolean insertMessage();
    public List<Message> selectMessage();
}
