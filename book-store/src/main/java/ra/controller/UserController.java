package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import ra.payload.request.LoginRequest;
import ra.payload.request.ResetPassword;
import ra.payload.request.SignupRequest;
import ra.sercurity.CustomUserDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    @PostMapping("/signup")
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
    @PostMapping("/signin")
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

    @GetMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");

        // Clear the authentication from server-side (in this case, Spring Security)
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("You have been logged out.");
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPassword resetPassword){
        CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUserId(customUserDetail.getUserId());
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

}
