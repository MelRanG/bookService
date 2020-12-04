package dto;

public class Book {

    private String bookName;
    private String category;
    private String img;
    private String href;
    private String plattform;

    public String getbookName(){
        return bookName;
    }

    public String getCategory(){
        return category;
    }

    public String getImg() {
        return img;
    }

    public String getHref() {
        return href;
    }

    public String getPlattform() {
        return plattform;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setPlattform(String plattform) {
        this.plattform = plattform;
    }

    @Override
    public String toString() {
        return "book_info{" +
                "bookName='" + bookName + '\'' +
                ", category='" + category + '\'' +
                ", img='" + img + '\'' +
                ", href='" + href + '\'' +
                ", plattform='" + plattform + '\'' +
                '}';
    }
}
