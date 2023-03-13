package ra.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
public class ResetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String token;
    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "userId",nullable = false)
    private User user;
    private Date startDate;
}
