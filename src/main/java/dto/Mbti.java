package dto;

public class Mbti {
    private int bookNo;
    private int mbtiNo;
    private String mbtiKey;
    private String bookName;
    private String category;
    private String img;
    private String href;
    private String plattform;

    public int getBookNo() {
        return bookNo;
    }

    public void setBookNo(int bookNo) {
        this.bookNo = bookNo;
    }

    public int getMbtiNo() {
        return mbtiNo;
    }

    public void setMbtiNo(int mbtiNo) {
        this.mbtiNo = mbtiNo;
    }

    public String getMbtiKey() {
        return mbtiKey;
    }

    public void setMbtiKey(String mbtiKey) {
        this.mbtiKey = mbtiKey;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getPlattform() {
        return plattform;
    }

    public void setPlattform(String plattform) {
        this.plattform = plattform;
    }

    @Override
    public String toString() {
        return "Mbti{" +
                "bookNo=" + bookNo +
                ", mbtiNo=" + mbtiNo +
                ", mbtiKey='" + mbtiKey + '\'' +
                ", bookName='" + bookName + '\'' +
                ", category='" + category + '\'' +
                ", img='" + img + '\'' +
                ", href='" + href + '\'' +
                ", plattform='" + plattform + '\'' +
                '}';
    }
}
