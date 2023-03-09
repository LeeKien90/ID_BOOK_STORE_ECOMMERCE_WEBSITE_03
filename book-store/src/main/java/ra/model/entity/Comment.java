package ra.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Comment")
public class Comment {
    @Column(name = "commentId")
    private int commentId;
    @Column(name = "content")
    private String content;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "bookId")
//    private Books books;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "userId")
//    private User user;
}
