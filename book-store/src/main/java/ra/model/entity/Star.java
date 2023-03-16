package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookId")
    @JsonIgnore
    private Books books;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;
}
