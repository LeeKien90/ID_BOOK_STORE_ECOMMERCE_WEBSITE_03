package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ra.jwt.JwtTokenProvider;
import ra.model.entity.ERoles;
import ra.model.entity.Roles;
import ra.model.entity.User;
import ra.model.service.RoleService;
import ra.model.service.UserService;
import ra.payload.reponse.JwtReponse;
import ra.payload.reponse.MessageReponse;
import ra.payload.request.*;
import ra.sercurity.CustomUserDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping()
    public List<User> getAllCatalog(){
        return  userService.findAll();
    }

    @GetMapping("/search")
    public List<User> searchByName(@RequestParam("name") String lastName){
        return userService.findByName(lastName);
    }

    @GetMapping("/sortByName")
    public ResponseEntity<List<User>> sortUserByUseName(@RequestParam("direction") String direction) {
        List<User> listUser = userService.softUseByUseName(direction);
        return new ResponseEntity<>(listUser, HttpStatus.OK);
    }
    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userService.existsByUserName(signupRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageReponse("Error: Usermame is already"));
        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageReponse("Error: Email is already"));
        }
        User user = new User();
        user.setUserName(signupRequest.getUserName());
        user.setUserPassword(encoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setPhone(signupRequest.getPhone());
        user.setUserStatus(true);
        Set<String> strRoles = signupRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if (strRoles==null){
            //User quyen mac dinh
            Roles userRole = roleService.findByRoleName(ERoles.ROLE_USER.ROLE_USER).orElseThrow(()->new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        }else {
            strRoles.forEach(role->{
                switch (role){
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(ERoles.ROLE_ADMIN)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
                        listRoles.add(adminRole);
                    case "moderator":
                        Roles modRole = roleService.findByRoleName(ERoles.ROLE_MODERATOR)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
                        listRoles.add(modRole);
                    case "user":
                        Roles userRole = roleService.findByRoleName(ERoles.ROLE_USER)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
                        listRoles.add(userRole);
                }
            });
        }
        user.setListRoles(listRoles);
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageReponse("User registered successfully"));
    }
    @PostMapping("/signIn")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        //Sinh JWT tra ve client
        String jwt = tokenProvider.generateToke(customUserDetail);
        //Lay cac quyen cua user
        List<String> listRoles = customUserDetail.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtReponse(jwt, customUserDetail.getUsername(), customUserDetail.getEmail(), customUserDetail.getPhone(), listRoles));
    }

    @GetMapping("/logOut")
    public ResponseEntity<?> logOut(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");

        // Clear the authentication from server-side (in this case, Spring Security)
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("You have been logged out.");
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody ResetPassword resetPassword){
        CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) userService.findById(customUserDetail.getUserId());
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        boolean checkPass = bc.matches(resetPassword.getOldPassword(), user.getUserPassword());
        if (checkPass) {
            boolean checkDuplicate = bc.matches(resetPassword.getNewPassword(), user.getUserPassword());
            if (checkDuplicate) {
                return ResponseEntity.ok(new MessageReponse("Mật khẩu mới phải khác mật khẩu cũ!"));
            } else {
                user.setUserPassword(encoder.encode(resetPassword.getNewPassword()));
                userService.saveOrUpdate(user);
                return ResponseEntity.ok(new MessageReponse("Đổi mật khẩu thành công !"));
            }
        } else {
            return ResponseEntity.ok(new MessageReponse("Mật khẩu không hợp lệ ! Đổi mật khẩu thất bại"));
        }
    }

    @PutMapping("/updateUser")
    public void updateUser(@RequestBody User user){
        CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userUpdate = (User) userService.findById(customUserDetail.getUserId());
        userUpdate.setFirstName(user.getFirstName());
        userUpdate.setLastName(user.getLastName());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setPhone(user.getPhone());
        userUpdate.setAddress(user.getAddress());
        userUpdate.setCity(user.getCity());
        userUpdate.setPostCode(user.getPostCode());
        userUpdate.setState(user.getState());
        userService.saveOrUpdate(userUpdate);
    }

    @PostMapping("/delete/{userId}")
    public User deleteUser(@PathVariable("userId") int userId, @RequestBody UserRequest userRequest){
        User user = (User) userService.findById(userId);
        if (!userRequest.isUserStatus()){
            user.setUserStatus(false);
            userService.saveOrUpdate(user);
        }else {
            user.setUserStatus(true);
            userService.saveOrUpdate(user);
        }
        return ResponseEntity.ok(user).getBody();
    }

    @PostMapping("/updateAuthorUser/{userId}")
    public User updateAuthorUser(@PathVariable("userId") int userId, @RequestBody UpdateAuthorUser updateAuthorUser){
        User user = (User) userService.findById(userId);
        Set<String> strRoles = updateAuthorUser.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if(strRoles == null){
            Roles userRole = roleService.findByRoleName(ERoles.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        }else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(ERoles.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(adminRole);
                    case "moderator":
                        Roles modRole = roleService.findByRoleName(ERoles.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(modRole);
                    case "user":
                        Roles userRole = roleService.findByRoleName(ERoles.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(userRole);
                }
            });
        }
        user.setListRoles(listRoles);
        return (User) userService.saveOrUpdate(user);
    }

    @GetMapping("/getPaggingUser")
    public ResponseEntity<Map<String,Object>> getPaggingUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<User> pageUser = userService.getPaggingUser(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("user",pageUser.getContent());
        data.put("total",pageUser.getSize());
        data.put("totalItems",pageUser.getTotalElements());
        data.put("totalPages",pageUser.getTotalPages());
        return new ResponseEntity<>(data,HttpStatus.OK);
    }


}
