package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.model.entity.OrderDetail;
import ra.model.entity.Orders;

import java.util.List;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    List<OrderDetail> findByBooks_BookName(String name);
    OrderDetail findByBooks_BookIdAndOrders_OrderId(int bookId,int orderId);
    List<OrderDetail> findByBooks_BookId(int bookId);
    List<OrderDetail> findByOrdersIn(List<Orders> listOrders);
//@Query(value = "select u.userId, od.bookId, od.price, od.quantity, od.total\n" +
//        "from orderdetail od join orders o on o.orderId = od.orderId join user u on o.userId = u.userId\n" +
//        "group by od.orderDetailId",nativeQuery = true)
//    List<OrderDetail> findOrderDetailByOrders(int id);
}
