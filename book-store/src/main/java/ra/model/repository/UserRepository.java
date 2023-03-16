package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.model.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmailContaining(String email);
    User findByUserName(String userName);
    List<User> findByLastNameContainingIgnoreCase(String lastName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    @Query(value = "select * from user  where firstName like '%' or lastName like '%'",nativeQuery = true)
    List<User> searchByFirstNameOrLastName(@Param("name") String name);
}
