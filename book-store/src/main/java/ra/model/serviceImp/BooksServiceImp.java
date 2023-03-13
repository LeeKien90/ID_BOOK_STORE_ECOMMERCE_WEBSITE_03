package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.model.entity.Books;
import ra.model.repository.BooksRepository;
import ra.model.service.BooksService;

import java.util.List;
@Service
public class BooksServiceImp implements BooksService<Books,Integer> {
    @Autowired
    private BooksRepository booksRepository;
    @Override
    public List<Books> findBookByBookName(String name) {
        return booksRepository.findByBookNameContainingIgnoreCase(name);
    }

    @Override
    public List<Books> sortBookByBookName(String direction) {
        if (direction.equals("asc")) {
            return booksRepository.findAll(Sort.by("bookName").ascending());
        } else {
            return booksRepository.findAll(Sort.by("bookName").descending());
        }
    }

    @Override
    public Page<Books> getPaggingBook(Pageable pageable) {
        return booksRepository.findAll(pageable);
    }

    @Override
    public List<Books> findAll() {
        try {
            return booksRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Books findById(Integer id) {
        return booksRepository.findById(id).get();
    }

    @Override
    public Books saveOrUpdate(Books books) {
        return booksRepository.save(books);
    }
}
