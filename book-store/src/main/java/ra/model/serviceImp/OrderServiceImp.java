package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Orders;
import ra.model.repository.OrderRepository;
import ra.model.service.OrderService;
import ra.payload.request.OrderCheckoutRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class OrderServiceImp implements OrderService<Orders,Integer> {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Orders findById(Integer id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public Orders saveOrUpdate(Orders orders) {
        return orderRepository.save(orders);
    }

    @Override
    public Orders findByUser_UserIdAndOrderStatus(int userId, int orderStatus) {
        return (Orders) orderRepository.findByUser_UserIdAndOrderStatus(userId,orderStatus);
    }

    @Override
    public Orders mapCartConfirmToCart(Orders orders, OrderCheckoutRequest checkoutRequest) {
        orders.setFirstName(checkoutRequest.getFirstName());
        orders.setLastName(checkoutRequest.getLastName());
        orders.setAddress(checkoutRequest.getAddress());
        orders.setCity(checkoutRequest.getCity());
        orders.setEmail(checkoutRequest.getEmail());
        orders.setCreated(LocalDateTime.now());
        orders.setPhone(checkoutRequest.getPhone());
        orders.setPostCode(checkoutRequest.getPostCode());
        orders.setState(checkoutRequest.getState());
        orders.setOrderStatus(1);
        return orders;
    }
}
