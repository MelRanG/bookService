package controller;
import dto.Mbti;
import dto.Book;
import dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.BookService;
import service.MessageInfoService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Controller
@RequestMapping(path = "/message.do")
public class MessageController {
    private final MessageInfoService msg_info;

    @Autowired
    public MessageController(MessageInfoService msg_info){
        this.msg_info = msg_info;
    }

    @GetMapping
    @ResponseBody
    public List<Message> gTest(){
        String url = "http://127.0.0.1:5000/message";
        String sb = "";
        try{
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

            String line = null;

            while ((line = br.readLine()) != null) {
                sb = sb + line + "\n";
            }

            if (sb.contains("ok")) {
                System.out.println("test");
            }
            br.close();
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        msg_info.insertMessage();
        List<Message> message = msg_info.selectMessage();
        return message;
    }
}
