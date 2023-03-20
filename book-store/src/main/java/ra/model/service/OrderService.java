package ra.model.service;

import ra.model.entity.Orders;
import ra.payload.request.OrderCheckoutRequest;

import java.util.List;

public interface OrderService<T,V> extends StoreBookService<T,V>{
    Orders findByUser_UserIdAndOrderStatus(int userId,int orderStatus);
    Orders mapCartConfirmToCart(Orders orders, OrderCheckoutRequest checkoutRequest);
    Orders findOrdersByUser_UserId(int id);
}
