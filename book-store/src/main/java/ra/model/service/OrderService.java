package ra.model.service;

import ra.model.entity.Orders;

import java.util.List;

public interface OrderService<T,V> extends StoreBookService<T,V>{
    Orders findByUser_UserIdAndOrderStatus(int userId,int orderStatus);
}
