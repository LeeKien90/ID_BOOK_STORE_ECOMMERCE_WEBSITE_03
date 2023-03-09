package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmailContaining(String email);
    User findByUserId(Integer userId);
    User findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
