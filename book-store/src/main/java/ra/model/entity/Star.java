package ra.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Star")
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "starId")
    private int starId;
    @Column(name = "starChoose")
    private int starChoose;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "bookId")
//    private Books books;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "userId")
//    private User user;
}
