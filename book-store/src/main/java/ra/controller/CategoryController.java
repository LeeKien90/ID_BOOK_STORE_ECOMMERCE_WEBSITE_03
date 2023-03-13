package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Category;
import ra.model.entity.User;
import ra.model.service.CategoryService;
import ra.payload.request.UserRequest;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping()
    public List<Category> getAll(){
        return categoryService.findAll();
    }

    @GetMapping("/search")
    public List<User> searchByName(@RequestParam("name") String categoryName){
        return categoryService.findByCategoryName(categoryName);
    }

    @GetMapping("/sortByName")
    public ResponseEntity<List<Category>> sortUserByUseName(@RequestParam("direction") String direction) {
        List<Category> listCategory = categoryService.sortCategoryByCategoryName(direction);
        return new ResponseEntity<>(listCategory, HttpStatus.OK);
    }

    @PostMapping("/create")
    public void createCategory(@RequestBody Category category){
        categoryService.saveOrUpdate(category);
    }

    @PutMapping("/update/{categoryId}")
    public void updateCategory(@PathVariable("categoryId") int categoryId,@RequestBody Category category){
        Category categoryUpdate = (Category) categoryService.findById(categoryId);
        categoryUpdate.setCategoryName(category.getCategoryName());
        categoryService.saveOrUpdate(categoryUpdate);
    }

    @PutMapping("/delete/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category){
        Category categoryDelete = (Category) categoryService.findById(categoryId);
       categoryDelete.setCategoryStatus(category.isCategoryStatus());
       categoryService.saveOrUpdate(categoryDelete);
    }
}
