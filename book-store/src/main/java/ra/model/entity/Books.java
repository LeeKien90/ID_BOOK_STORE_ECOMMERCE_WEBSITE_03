package ra.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "Books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookId")
    private int bookId;
    @Column(name = "bookName")
    private String bookName;
    @Column(name = "image")
    private String image;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "years")
    private int years;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private float price;
    @Column(name = "ediitonLanguage")
    private String ediitonLanguage;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "sale")
    private int sale;
    @Column(name = "bookFormat")
    private String bookFormat;
    @Column(name = "bookStatus")
    private boolean bookStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId")
    private Category category;


    @OneToMany(mappedBy = "books")
    private List<TagBooks> tagBooks = new ArrayList<>();
    @OneToMany(mappedBy = "books")
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
