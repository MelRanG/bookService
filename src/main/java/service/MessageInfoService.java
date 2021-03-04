package service;

import dto.Message;

import java.util.List;

public interface MessageInfoService {
    public void insertMessage();
    public List<Message> selectMessage();
}
