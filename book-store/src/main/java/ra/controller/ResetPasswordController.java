package ra.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.ResetPassword;
import ra.model.entity.User;
import ra.model.sendEmail.SendEmail;
import ra.model.service.ResetPasswordService;
import ra.model.service.UserService;
import ra.payload.reponse.MessageReponse;
import ra.sercurity.CustomUserDetail;
import ra.sercurity.CustomUserDetailService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ResetPasswordController {
    private PasswordEncoder encoder;
    private SendEmail sendEmail;
    private ResetPasswordService resetPasswordService;
    private UserService userService;
    private CustomUserDetailService customUserDetailService;

    @GetMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String userEmail, HttpServletRequest request) {

        if (userService.existsByEmail(userEmail)) {
            User users = (User) userService.findByEmail(userEmail);
            UserDetails userDetails = customUserDetailService.loadUserByUsername(users.getUserName());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = UUID.randomUUID().toString();
            ResetPassword myToken = new ResetPassword();
            myToken.setToken( token);
            String mess= "token is valid for 5 minutes.\n"+"Your token: " +token;
            myToken.setUser(users);
            Date now = new Date();
            myToken.setStartDate(now);
            resetPasswordService.saveOrUpdate(myToken);
            sendEmail.sendSimpleMessage(users.getEmail(),
                    "Reset your password", mess);
            return ResponseEntity.ok("Email sent! Please check your email");
        } else {
            return new ResponseEntity<>(new MessageReponse("Email is not already"), HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PutMapping("/creatNewPass")
    public ResponseEntity<?> creatNewPass(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResetPassword passwordResetToken = resetPasswordService.getLastTokenByUserId(userDetails.getUserId());
        long date1 = passwordResetToken.getStartDate().getTime() + 1800000;
        long date2 = new Date().getTime();
        if (date2 > date1) {
            return new ResponseEntity<>(new MessageReponse("Expired Token "), HttpStatus.EXPECTATION_FAILED);
        } else {
            if (passwordResetToken.getToken().equals(token)) {
                User users = (User) userService.findById(userDetails.getUserId());
                users.setUserPassword(encoder.encode(newPassword));
                userService.saveOrUpdate(users);
                return new ResponseEntity<>(new MessageReponse("update password successfully "), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageReponse("token is fail "), HttpStatus.NO_CONTENT);
            }
        }
    }
}
