package ra.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private int orderId;
    @Column(name = "total")
    private float total;
    @Column(name = "created")
    private Date created;
//    @OneToMany(mappedBy = "order")
//    private List<OrderDetail> orderDetails = new ArrayList<>();
//    @ManyToOne(fetch = FetchType.EAGER)
//    private User user;


}
