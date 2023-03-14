package ra.model.service;

import org.springframework.stereotype.Repository;
import ra.model.entity.OrderDetail;
import ra.model.entity.Orders;

import java.util.List;

@Repository
public interface OrderDetailService<T,V> extends StoreBookService<T,V>{
    OrderDetail findByBooks_BookIdAndOrders_OrderId(int bookId,int orderId);
    boolean deleteByOrderDetailId(int id);
    List<OrderDetail> findByBooks_BookId(int bookId);
    List<OrderDetail> findByOrdersIn(List<Orders> listOrders);
    List<OrderDetail> findByBooks_BookName(String name);
}
