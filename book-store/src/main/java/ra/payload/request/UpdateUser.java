package ra.payload.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUser {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private int postCode;
    private String state;
}
