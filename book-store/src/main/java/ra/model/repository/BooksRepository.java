package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Books;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Books,Integer> {
    List<Books> findByBookNameContainingIgnoreCase(String bookName);
}
