package ra.model.service;

import ra.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findByUserId(Integer userId);
    User findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    User saveOrUpdate(User user);
}
