package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.model.entity.User;
import ra.model.repository.UserRepository;
import ra.model.service.UserService;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUserId(Integer userId) {
        return userRepository.findByUserId(userId);
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
    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }

    @Override
    public Page<User> getPaggingUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
