package ra.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.entity.Books;


import java.util.List;

public interface BooksService<T,V> extends StoreBookService<T,V>{
    List<Books> findBookByBookName(String name);
    List<Books> sortBookByBookName(String direction);
    Page<Books> getPaggingBook(Pageable pageable);

}
