package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Books;
import ra.model.entity.Comment;
import ra.model.entity.User;
import ra.model.service.BooksService;
import ra.model.service.CommentService;
import ra.model.service.UserService;
import ra.payload.request.CommentRequest;
import ra.sercurity.CustomUserDetail;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private BooksService booksService;


    @PostMapping("/createComment")
    public Comment createComment(@RequestBody CommentRequest commentRequest){
        CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) userService.findById(customUserDetail.getUserId());
        Books books = (Books) booksService.findById(commentRequest.getBookId());
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setBooks(books);
        comment.setContent(commentRequest.getContent());
        comment.setStatus(true);
        return (Comment) commentService.saveOrUpdate(comment);
    }

    @PutMapping("/updateComment/{commentId}")
    public Comment updateComment(@PathVariable("commentId")int commentId, @RequestBody Comment comment){
        Comment commentUpdate = (Comment) commentService.findById(commentId);
        commentUpdate.setContent(comment.getContent());
        return (Comment) commentService.saveOrUpdate(commentUpdate);
    }

    @PostMapping("/deleteComment/{commentId}")
    public Comment deleteComment(@PathVariable("commentId") int commentId){
        Comment comment = (Comment) commentService.findById(commentId);
        comment.getContent();
        comment.setStatus(false);
        return (Comment) commentService.saveOrUpdate(comment);
    }
}
