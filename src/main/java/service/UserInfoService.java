package service;

import dto.User;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.List;


public interface UserInfoService {
    public int insertUserInfo(User user_) throws NoSuchAlgorithmException;
    public List<User> checkLogin(String userId);
    public void logout(HttpSession session);
    public int findById(String userId);
}
