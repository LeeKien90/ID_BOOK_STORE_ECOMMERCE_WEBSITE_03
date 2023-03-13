package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.model.entity.Category;
import ra.model.repository.CategoryRepository;
import ra.model.service.CategoryService;

import java.util.List;
@Service
public class CategoryServiceImp implements CategoryService<Category,Integer> {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> findByCategoryName(String name) {
        return categoryRepository.findByCategoryNameContainingIgnoreCase(name);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Integer id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public Category saveOrUpdate(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> sortCategoryByCategoryName(String direction) {
        if (direction.equals("asc")) {
            return categoryRepository.findAll(Sort.by("categoryName").ascending());
        } else {
            return categoryRepository.findAll(Sort.by("categoryName").descending());
        }
    }
}
