package service.Impl;

import dao.User_InfoDao;
import dto.User_Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.User_InfoService;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class User_InfoImpl implements User_InfoService {
    private final User_InfoDao user_infoDao;

    @Autowired
    public User_InfoImpl(User_InfoDao user_infoDao){
        this.user_infoDao = user_infoDao;
    }

    @Override
    @Transactional(readOnly = false)
    public User_Info insertUser_info(User_Info user_info) {
        String password = user_info.getPassword();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            // 암호화 처리된 게 bytes 안에 있음(아직)
            byte[] bytes = password.getBytes(Charset.forName("UTF-8"));
            md.update(bytes);
            // 암호화 처리 된게 문자열로 바뀐다.
            String encPwd = Base64.getEncoder().encodeToString(md.digest());
            System.out.println(encPwd);
            user_info.setPassword(encPwd);
        }
        catch (NoSuchAlgorithmException e){e.printStackTrace();}
        user_infoDao.insert(user_info);
        return user_info;
    }

    @Override
    public List<User_Info> checkLogin(String userId) {
        return user_infoDao.checkLogin(userId);
    }

    @Override
    public int checkId(String userId) {
        return user_infoDao.checkId(userId);
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
