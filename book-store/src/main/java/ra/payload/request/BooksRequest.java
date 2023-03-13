package ra.payload.request;

import lombok.Data;


@Data
public class BooksRequest {
    private int bookId;
    private String bookName;
    private String image;
    private String title;
    private String author;
    private int years;
    private int quantity;
    private float price;
    private String ediitonLanguage;
    private String publisher;
    private int sale;
    private String bookFormat;
    private boolean bookStatus;
    private int categoryId;
}
