package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Books;
import ra.model.entity.Category;
import ra.model.entity.User;
import ra.model.service.BooksService;
import ra.model.service.CategoryService;
import ra.payload.request.BooksDeleteRequest;
import ra.payload.request.BooksRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/books")
public class BooksController {
    @Autowired
    private BooksService booksService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public List<Books> getAll() {
        return booksService.findAll();
    }

    @GetMapping("/search")
    public List<User> searchByName(@RequestParam("name") String bookName){
        return booksService.findBookByBookName(bookName);
    }

    @GetMapping("/sortByName")
    public ResponseEntity<List<Books>> sortBookByBookName(@RequestParam("direction") String direction) {
        List<Books> listBooks = booksService.sortBookByBookName(direction);
        return new ResponseEntity<>(listBooks, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> createBooks(@RequestBody BooksRequest booksRequest) {
        try {
            Books book = new Books();
            book.setBookName(booksRequest.getBookName());
            book.setImage(booksRequest.getImage());
            book.setTitle(booksRequest.getTitle());
            book.setAuthor(booksRequest.getAuthor());
            book.setYears(booksRequest.getYears());
            book.setQuantity(booksRequest.getQuantity());
            book.setPrice(booksRequest.getPrice());
            book.setEdiitonLanguage((booksRequest.getEdiitonLanguage()));
            book.setPublisher(booksRequest.getPublisher());
            book.setBookFormat(booksRequest.getBookFormat());
            book.setBookStatus(booksRequest.isBookStatus());
            Category category = (Category) categoryService.findById(booksRequest.getCategoryId());
            book.setCategory(category);
            booksService.saveOrUpdate(book);
            return ResponseEntity.ok("Creat new books successfully");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("ERROR!");
        }
    }

    @PutMapping("/update/{bookId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> updateBooks(@PathVariable("bookId") int bookId, @RequestBody BooksRequest booksRequest) {
        try {
            Books book = (Books) booksService.findById(bookId);
            book.setBookName(booksRequest.getBookName());
            book.setImage(booksRequest.getImage());
            book.setTitle(booksRequest.getTitle());
            book.setAuthor(booksRequest.getAuthor());
            book.setYears(booksRequest.getYears());
            book.setQuantity(booksRequest.getQuantity());
            book.setPrice(booksRequest.getPrice());
            book.setEdiitonLanguage((booksRequest.getEdiitonLanguage()));
            book.setPublisher(booksRequest.getPublisher());
            book.setBookFormat(booksRequest.getBookFormat());
            book.setBookStatus(booksRequest.isBookStatus());
            Category category = (Category) categoryService.findById(booksRequest.getCategoryId());
            book.setCategory(category);
            booksService.saveOrUpdate(book);
            return ResponseEntity.ok("Update books successfully");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("ERROR!");
        }
    }

    @PutMapping("/delete/{bookId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> updateBooks(@PathVariable("bookId") int bookId, @RequestBody BooksDeleteRequest booksDeleteRequest){
        Books book = (Books) booksService.findById(bookId);
        book.setBookStatus(booksDeleteRequest.isBookStatus());
        booksService.saveOrUpdate(book);
        return ResponseEntity.ok("Delete book successfully");
    }

    @GetMapping("/getPagging")
    public ResponseEntity<Map<String,Object>> getPaggingBook(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Books> pageBook = booksService.getPaggingBook(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("user",pageBook.getContent());
        data.put("total",pageBook.getSize());
        data.put("totalItems",pageBook.getTotalElements());
        data.put("totalPages",pageBook.getTotalPages());
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

}
