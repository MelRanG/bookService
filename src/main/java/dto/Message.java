package dto;

import java.util.Date;

public class Message {
    private int mNo;
    private String userId;
    private String message;
    private String sendDate;

    public int getmNo() {
        return mNo;
    }

    public void setmNo(int mNo) {
        this.mNo = mNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mNo=" + mNo +
                ", userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                ", sendDate='" + sendDate + '\'' +
                '}';
    }
}
