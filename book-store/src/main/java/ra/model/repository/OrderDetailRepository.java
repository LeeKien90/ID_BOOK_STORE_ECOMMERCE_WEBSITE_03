package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
