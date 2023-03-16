package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.model.entity.Books;
import ra.model.entity.User;
import ra.model.repository.BooksRepository;
import ra.model.repository.UserRepository;
import ra.model.service.UserService;

import java.util.List;

@Service
public class UserServiceImp implements UserService<User,Integer> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BooksRepository booksRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmailContaining(email);
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByLastNameContainingIgnoreCase(name);
    }

    @Override
    public List<User> softUseByUseName(String direction) {
        if (direction.equals("asc")) {
            return userRepository.findAll(Sort.by("lastName").ascending());
        } else {
            return userRepository.findAll(Sort.by("lastName").descending());
        }
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    public Page<User> getPaggingUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public boolean addOrDeleteWishList(int userId, int bookId, String action) {
        User user = userRepository.findById(userId).get();
        boolean check = false;
        if (action.equals("add")){
            // Nếu add vào wishList thì sản phẩm đã có hay không add vào yêu thích cũng được!
            check = true;
            user.getWishList().add(booksRepository.findById(bookId).get());
        }else {
            for (Books books :user.getWishList()) {
                if (books.getBookId()==bookId){
                    user.getWishList().remove(books);
                    check =true; // Nếu sản phẩm đã có trong yêu thích thì return true;
                    break;
                }
            }
        }
        if (check){
            try {
                userRepository.save(user);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else {
            // sản phẩm chưa có trong danh sách yêu thích
            return false;
        }
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> findByFirstNameOrLasName(String name) {
        return userRepository.searchByFirstNameOrLastName(name);
    }
}
