package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Orders;
import ra.model.repository.OrderRepository;
import ra.model.service.OrderService;

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
        return orderRepository.findByUser_UserIdAndOrderStatus(userId,orderStatus);
    }
}
