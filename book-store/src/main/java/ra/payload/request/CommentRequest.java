package ra.payload.request;

import lombok.Data;

@Data
public class CommentRequest {
    private int bookId;
    private String content;
    private boolean status;

}
