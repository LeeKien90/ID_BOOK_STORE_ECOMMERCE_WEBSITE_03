package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.model.entity.Books;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Books,Integer> {
    List<Books> findByBookNameContainingIgnoreCase(String bookName);
    @Query(value = "select * from book b join wishlist w on b.bookId = w.bookId where w.userId = :uId",nativeQuery = true)
    List<Books> getAllWishList(@Param("uId") int userId);
}
