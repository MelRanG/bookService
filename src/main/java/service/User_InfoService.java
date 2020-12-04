package service;

import dto.Login;
import dto.User_Info;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.List;


public interface User_InfoService {
    public User_Info insertUser_info(User_Info user_info) throws NoSuchAlgorithmException;
    public List<User_Info> checkLogin(String userId);
    public void logout(HttpSession session);
    public int checkId(String userId);
}
