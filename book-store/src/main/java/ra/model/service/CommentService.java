package ra.model.service;

public interface CommentService<T,V> extends StoreBookService<T,V>{
    void delete(int commentId);

}
