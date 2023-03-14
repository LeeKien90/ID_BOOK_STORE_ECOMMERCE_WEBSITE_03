package ra.payload.request;

import lombok.Data;

@Data
public class OrderDetailRequest {
    private int bookId;
    private int quantity;

}
