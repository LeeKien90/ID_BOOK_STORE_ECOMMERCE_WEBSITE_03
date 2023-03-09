package ra.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlistId")
    private int wishlistId;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "bookId")
//    private Books books;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "userId")
//    private User user;
}
