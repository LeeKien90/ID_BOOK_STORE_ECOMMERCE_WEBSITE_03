package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag,Integer> {
}
