package ra.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private int userId;
    @Column(name = "UserName",unique = true,nullable = false)
    private String userName;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "userPassword",nullable = false)
    private String userPassword;
    @Column(name = "Email",nullable = false,unique = true)
    private String email;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Address")
    private String address;
    @Column(name = "City")
    private String city;
    @Column(name = "PostCode")
    private int postCode;
    @Column(name = "State")
    private String state;
    @Column(name = "UserStatus")
    private boolean userStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Role",joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "RoleId"))
    private Set<Roles> listRoles = new HashSet<>();

//    @OneToMany(mappedBy = "user")
//    private List<Wishlist> wishlists = new ArrayList<>();
//    @OneToMany(mappedBy = "user")
//    private List<Star> stars = new ArrayList<>();
//    @OneToMany(mappedBy = "user")
//    private List<Comment> comments = new ArrayList<>();
//    @OneToMany(mappedBy = "user")
//    private List<Order> orders = new ArrayList<>();

}
