package service.Impl;

import dao.MessageDao;
import dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.MessageInfoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MessageInfoServiceImpl implements MessageInfoService {
    private final MessageDao messageDao;

    @Autowired
    public MessageInfoServiceImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public void insertMessage() {
        String msg = get_message();
        deleteMessage();
        if ("".equals(msg)){
            System.out.println("false");
        }
        else{
            String date = get_date();
            Message message = new Message();
            message.setMessage(get_message());
            message.setSendDate(date);
            messageDao.insert(message);
        }
    }

    public void deleteMessage(){
        int count = messageDao.selectMessageCount();
        while(count > 5){
            messageDao.deleteMessage();
            count = messageDao.selectMessageCount();
            System.out.println("count: " + count);
        }
    }

    @Override
    public List<Message> selectMessage() {
        List<Message> msg = messageDao.selectMessage();
        return msg;
    }

    public String get_message(){
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
        return sb;
    }

    public static String get_date(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }
}
