package ra.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findByUserId(Integer userId);
    User findByEmail(String email);
    List<User> findByName(String name);
    List<User> softUseByUseName(String direction);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    User saveOrUpdate(User user);
    Page<User> getPaggingUser(Pageable pageable);
}
