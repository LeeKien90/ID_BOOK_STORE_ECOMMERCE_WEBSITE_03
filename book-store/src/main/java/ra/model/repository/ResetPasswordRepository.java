package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.model.entity.ResetPassword;
@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword,Integer> {
    @Query(value = "select id, startDate, token, user_id\n" +
            "    from PasswordResetToken\n" +
            "where id= (select max(id)\n" +
            "           from PasswordResetToken\n" +
            "           where user_id = :uId)", nativeQuery = true)
    ResetPassword getLastTokenByUserId(@Param("uId")int uId);
}
