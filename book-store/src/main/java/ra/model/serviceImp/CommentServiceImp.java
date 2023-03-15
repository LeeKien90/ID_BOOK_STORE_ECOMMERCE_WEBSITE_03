package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Comment;
import ra.model.repository.CommentRepository;
import ra.model.service.CommentService;

import java.util.List;
@Service
public class CommentServiceImp implements CommentService<Comment,Integer> {
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public void delete(int commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findCommentByStatus(true);
    }

    @Override
    public Comment findById(Integer id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public Comment saveOrUpdate(Comment comment) {
        return commentRepository.save(comment);
    }
}
