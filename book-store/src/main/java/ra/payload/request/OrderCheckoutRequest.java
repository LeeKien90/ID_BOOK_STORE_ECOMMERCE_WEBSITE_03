package ra.payload.request;

import lombok.Data;

import javax.persistence.Column;

@Data
public class OrderCheckoutRequest {
    private int orderId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private int postCode;
    private String state;
    private String note;
}
