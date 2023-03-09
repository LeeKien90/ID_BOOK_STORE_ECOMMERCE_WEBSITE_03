package ra.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "OrderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderDetailId")
    private int orderDetailId;
    @Column(name = "price")
    private float price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "totalDetail")
    private float totalDetail;
//    @OneToMany(mappedBy = "orderDetail")
//    private List<Books> books = new ArrayList<>();
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "orderId")
//    private Order order;
}
