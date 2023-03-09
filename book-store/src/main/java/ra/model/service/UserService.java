package ra.model.service;

import ra.model.entity.User;

public interface UserService {
    User findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    User saveOrUpdate(User user);
}
