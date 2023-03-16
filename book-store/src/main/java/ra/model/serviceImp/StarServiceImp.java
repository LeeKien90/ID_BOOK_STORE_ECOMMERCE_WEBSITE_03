package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Books;
import ra.model.entity.Star;
import ra.model.entity.User;
import ra.model.repository.BooksRepository;
import ra.model.repository.StarRepository;
import ra.model.repository.UserRepository;
import ra.model.service.StarService;
import ra.payload.request.StarRequest;

import java.util.List;
@Service
public class StarServiceImp implements StarService<Star,Integer> {
    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StarRepository starRepository;
    @Override
    public Star findByBookIdAndUserId(Integer bookId, Integer userId) {
        return starRepository.findByBooks_BookIdAndUser_UserId(bookId,userId);
    }

    @Override
    public Star mapRequestToStar(Integer userId, StarRequest startRequest) {
        Books book= booksRepository.findById(startRequest.getBookId()).get();
        User users= userRepository.findById(userId).get();
        Star star = new Star();
        star.setStarChoose(startRequest.getStarChoose());
        star.setUser(users);
        star.setBooks(book);
        return star;
    }

    @Override
    public List<Star> findAll() {
        return starRepository.findAll();
    }

    @Override
    public Star findById(Integer id) {
        return starRepository.findById(id).get();
    }

    @Override
    public Star saveOrUpdate(Star star) {
        return starRepository.save(star);
    }
}
