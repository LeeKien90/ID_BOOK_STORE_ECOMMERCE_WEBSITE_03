package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.OrderDetail;
import ra.model.entity.Orders;
import ra.model.repository.OrderDetailRepository;
import ra.model.service.OrderDetailService;

import java.util.List;
@Service
public class OrderDetailServiceImp implements OrderDetailService<OrderDetail,Integer> {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail findByBooks_BookIdAndOrders_OrderId(int bookId, int orderId) {
        return orderDetailRepository.findByBooks_BookIdAndOrders_OrderId(bookId,orderId);
    }

    @Override
    public boolean deleteByOrderDetailId(int id) {
        try {
        orderDetailRepository.deleteById(id);
        return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<OrderDetail> findByBooks_BookId(int bookId) {
        return orderDetailRepository.findByBooks_BookId(bookId);
    }

    @Override
    public List<OrderDetail> findByOrdersIn(List<Orders> listOrders) {
        return orderDetailRepository.findByOrdersIn(listOrders);
    }

    @Override
    public List<OrderDetail> findByBooks_BookName(String name) {
        return orderDetailRepository.findByBooks_BookName(name);
    }



    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public OrderDetail findById(Integer id) {
        return orderDetailRepository.findById(id).get();
    }

    @Override
    public OrderDetail saveOrUpdate(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
