package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Books;
import ra.model.entity.OrderDetail;
import ra.model.entity.Orders;
import ra.model.entity.User;
import ra.model.service.BooksService;
import ra.model.service.OrderDetailService;
import ra.model.service.OrderService;
import ra.model.service.UserService;
import ra.payload.request.OrderDetailRequest;
import ra.payload.request.UpdateOrderDetail;
import ra.sercurity.CustomUserDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private BooksService booksService;
    @Autowired
    private UserService userService;

    @GetMapping()
    public List<OrderDetail> getAll(){
        return orderDetailService.findAll();
    }
    @PutMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestBody OrderDetailRequest orderDetailRequest) {
        CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (customUserDetail!=null){
            Orders orders = orderService.findByUser_UserIdAndOrderStatus(customUserDetail.getUserId(), 0);
            if (orders == null) {
                Orders order = new Orders();
                order.setUser((User) userService.findById(customUserDetail.getUserId()));
                order.setOrderStatus(0);
                orders = (Orders) orderService.saveOrUpdate(order);
            }
            try {

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrders(orders);
                Books books = (Books) booksService.findById(orderDetailRequest.getBookId());
                orderDetail.setBooks(books);
                orderDetail.setQuantity(orderDetailRequest.getQuantity());
                orderDetail.setPrice(books.getPrice() * orderDetailRequest.getQuantity());
                for (OrderDetail o : orders.getOrderDetails()) {
                    if (o.getBooks().getBookId() == books.getBookId()) {
                        orderDetail.setQuantity(orderDetailRequest.getQuantity() + o.getQuantity());
                        orderDetail.setPrice(books.getPrice());
                        orderDetail.setTotal(books.getPrice() * orderDetail.getQuantity());
                        // dòng 61 để gộp hết orderDetail cùng id vào làm 1
                        orderDetail.setOrderDetailId(o.getOrderDetailId());
                        break;
                    }
                }
                orderDetail = (OrderDetail) orderDetailService.saveOrUpdate(orderDetail);
                return ResponseEntity.ok(orderDetail);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok("CartDetail create fail");
            }
        }else {
            return ResponseEntity.ok("You can login");
        }
    }

    @PutMapping("/updateCart/{orderDetailId}")
    public ResponseEntity<?> updateCart(@PathVariable("orderDetailId")int orderDetailId, @RequestBody UpdateOrderDetail updateOrderDetail){
        OrderDetail orderDetail = (OrderDetail) orderDetailService.findById(orderDetailId);
        orderDetail.getBooks();
        orderDetail.setQuantity(updateOrderDetail.getQuantity());
        orderDetail.setTotal(orderDetail.getPrice()*updateOrderDetail.getQuantity());
        orderDetailService.saveOrUpdate(orderDetail);
        return ResponseEntity.ok(orderDetail);
    }

    @DeleteMapping("/deleteCartDetail/{detailId}")
    public ResponseEntity<?> deleteCartDetail(@PathVariable int detailId) {
        try {
            orderDetailService.deleteByOrderDetailId(detailId);
            return ResponseEntity.ok().body("Delete successfully");
        } catch (Exception ex) {
            return ResponseEntity.ok().body("Delete error");
        }
    }
}
