package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.*;
import ra.model.service.*;
import ra.payload.request.StarRequest;
import ra.sercurity.CustomUserDetail;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/star")
public class StarController {
    @Autowired
    private StarService starService;
    @Autowired
    private BooksService booksService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/voteStar")
    public ResponseEntity<?> creatNewStar(@RequestBody StarRequest starRequest) {
                try {
            CustomUserDetail usersChangePass = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = (User) userService.findByUserName(usersChangePass.getUsername());
            Star oldStar = starService.findByBookIdAndUserId(starRequest.getBookId(), user.getUserId());
            if (oldStar != null) {
                oldStar.setStarChoose(starRequest.getStarChoose());
                Star result = (Star) starService.saveOrUpdate(oldStar);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                Orders orders = (Orders) orderService.findByUser_UserIdAndOrderStatus(user.getUserId(), 1);
                List<OrderDetail> listCartDetail = orderDetailService.findByOrdersIn((List<Orders>) orders);
                boolean check = false;
                for (OrderDetail orderDetail : listCartDetail) {
                    if (orderDetail.getBooks().getBookId()== starRequest.getBookId()){
                        check= true;
                        break;
                    }
                }
                if (check){
                    Star star = starService.mapRequestToStar(user.getUserId(), starRequest);
                    Star result = (Star) starService.saveOrUpdate(star);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }else {
                    return new ResponseEntity<>( "Buy before rate",HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
