public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private String content;

    public Book(int id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(int id, String title, String author, String genre, String content) {
        this(id, title, author, genre);
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getContent() {
        return content;
    }
}