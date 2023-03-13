package ra.model.service;

import ra.model.entity.Category;

import java.util.List;

public interface CategoryService<T,V> extends StoreBookService<T,V>{
    List<Category> findByCategoryName(String name);
    List<Category> sortCategoryByCategoryName(String direction);
}
