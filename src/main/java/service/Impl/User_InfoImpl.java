package service.Impl;

import dao.UserDao;
import dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.UserInfoService;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class User_InfoImpl implements UserInfoService {
    private final UserDao user_Dao;

    @Autowired
    public User_InfoImpl(UserDao user_Dao){
        this.user_Dao = user_Dao;
    }

    @Override
    @Transactional(readOnly = false)
    public int insertUserInfo(User user) {
        String password = user.getPassword();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            // 암호화 처리된 게 bytes 안에 있음(아직)
            byte[] bytes = password.getBytes(Charset.forName("UTF-8"));
            md.update(bytes);
            // 암호화 처리 된게 문자열로 바뀐다.
            String encPwd = Base64.getEncoder().encodeToString(md.digest());
            user.setPassword(encPwd);
        }
        catch (NoSuchAlgorithmException e){e.printStackTrace();}
        return user_Dao.insert(user);
    }

    @Override
    public List<User> checkLogin(String userId) {
        return user_Dao.checkLogin(userId);
    }

    @Override
    public int findById(String userId) {
        return user_Dao.checkId(userId);
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
