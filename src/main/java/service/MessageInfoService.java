package service;

import dto.Message;

import java.util.List;

public interface MessageInfoService {
    public List<Message> selectMessage();
}
