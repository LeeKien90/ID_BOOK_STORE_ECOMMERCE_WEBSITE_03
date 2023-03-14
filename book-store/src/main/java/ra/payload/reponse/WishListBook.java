package ra.payload.reponse;

import lombok.Data;
import ra.model.entity.Category;
@Data
public class WishListBook {
    private int bookId;
    private String bookName;
    private String image;
    private String title;
    private float price;
    private int sale;
    private Category category;
}
