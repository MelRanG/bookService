package dto;

public class User_Info {
    private String userId;
    private String password;
    private String name;
    private String sex;
    private Integer birth;
    private String bookGenre;
    private String job;
    private String readRecently;


    public void setReadRecently(String readRecently) {
        this.readRecently = readRecently;
    }

    public String getReadRecently() {
        return readRecently;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirth(Integer birth) {
        this.birth = birth;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public Integer getBirth() {
        return birth;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public String getJob() {
        return job;
    }

    @Override
    public String toString() {
        return "User_Info{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birth=" + birth +
                ", bookGenre='" + bookGenre + '\'' +
                ", job='" + job + '\'' +
                ", readRecently='" + readRecently + '\'' +
                '}';
    }
}
