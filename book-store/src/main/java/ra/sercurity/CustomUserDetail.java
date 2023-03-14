package ra.sercurity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.model.entity.Orders;
import ra.model.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CustomUserDetail implements UserDetails {
    private int userId;
    private String userName;
//    private String firstName;
//    private String lastName;

    @JsonIgnore
    private String password;
    private String email;
    private String phone;
//    private String address;
//    private String city;
//    private int postCode;
//    private String state;
    private boolean userStatus;
//    List<Orders> orders;
    private Collection<? extends GrantedAuthority> authorities;

    public static CustomUserDetail mapUserToUserDetail(User user) {
        //Lay cac quyen tu doi tuong user
        List<GrantedAuthority> listAuthorities = user.getListRoles().stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRoleName().name()))
                .collect(Collectors.toList());
        //Tra ve doi tuong CustomUserDetails
        return new CustomUserDetail(
                user.getUserId(),
                user.getUserName(),
//                user.getFirstName(),
//                user.getLastName(),
                user.getUserPassword(),
                user.getEmail(),
                user.getPhone(),
//                user.getAddress(),
//                user.getCity(),
//                user.getPostCode(),
//                user.getState(),
                user.isUserStatus(),
//                user.getOrders(),
                listAuthorities
        );

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
