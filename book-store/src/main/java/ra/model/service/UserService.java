package ra.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.entity.User;

import java.util.List;

public interface UserService<T,V> extends StoreBookService<T,V>{
    User findByEmail(String email);
    List<User> findByName(String name);
    List<User> softUseByUseName(String direction);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Page<User> getPaggingUser(Pageable pageable);
    boolean addOrDeleteWishList(int userId,int bookId,String action);
    User findByUserName(String userName);
    List<User> findByFirstNameOrLasName(String name);
}
