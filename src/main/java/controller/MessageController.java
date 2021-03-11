package controller;
import dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.MessageInfoService;
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
        List<Message> message = msg_info.selectMessage();
        return message;

    }
}
