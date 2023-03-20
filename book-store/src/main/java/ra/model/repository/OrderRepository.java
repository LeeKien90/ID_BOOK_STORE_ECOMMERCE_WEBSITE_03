package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Orders;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
    Orders findByUser_UserIdAndOrderStatus(int userId,int orderStatus);
    Orders findOrdersByUser_UserId(int id);

}
