package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
