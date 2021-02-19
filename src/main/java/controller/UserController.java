package controller;

import dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.UserInfoService;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;


@Controller
public class UserController {
    private final UserInfoService user_infoService;

    @Autowired
    public UserController(UserInfoService user_infoService){
        this.user_infoService = user_infoService;
    }

    @GetMapping(path = "/register")
    public String register(){
        return "register";
    }

    @PostMapping(path = "/register")
    public String register(@ModelAttribute User user_){
        System.out.println(user_);
        try {user_infoService.insertUserInfo(user_);

        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/list";
    }

    @GetMapping(path = "/register/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("userId") String userId){
        System.out.println("checkId = " + user_infoService.findById(userId));
        return user_infoService.findById(userId);
    }

    @PostMapping(path = "/login")
    public String login(@RequestParam(name = "userId")String userId,
                      @RequestParam(name = "password")String password,
                      HttpServletRequest request, ModelMap model) throws NoSuchAlgorithmException {
        List<User> userInfo = user_infoService.checkLogin(userId);

        if(userInfo.isEmpty() == false){
            String cPwd = userInfo.get(0).getPassword();
            String cId =  userInfo.get(0).getUserId();

            //패스워드 암호화 처리
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = password.getBytes(Charset.forName("UTF-8"));
            md.update(bytes);
            String encPwd = Base64.getEncoder().encodeToString(md.digest());

            if((cId.equals(userId)) && (cPwd.equals(encPwd))) {
                System.out.println("로그인 성공");
                request.getSession().setAttribute("userId", userId);
                request.getSession().setAttribute("userInfo", userInfo);
                model.addAttribute("loginCheck", true);

            } else{
                System.out.println("비밀번호 불일치");
                model.addAttribute("loginCheck", 1);
                return "login";
        }
        } else{
            System.out.println("아이디 불일치");
            model.addAttribute("loginCheck", 2);
            return "login";
        }

        return "redirect:/list?user=" + userId;
    }

    @RequestMapping(value = "/joinForm")
    public String join(){
        return "register";
    }
    @RequestMapping(value = "/logout")
    public String logout(){
        return "logout";
    }
    @GetMapping(value = "/login")
    public String login(){ return "login";}

}
