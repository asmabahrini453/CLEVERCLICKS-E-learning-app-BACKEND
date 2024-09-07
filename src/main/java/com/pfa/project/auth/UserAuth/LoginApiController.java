package com.pfa.project.auth.UserAuth;

import com.pfa.project.Entities.User;
import com.pfa.project.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pfa/user")
@RequiredArgsConstructor
public class LoginApiController {

    @Autowired
    UserService userService;

  /*  @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest login){

        // Get User Email:
        List<String> userEmail = userService.checkUserEmail(login.getEmail());

        // Check If Email Is Empty:
        if(userEmail.isEmpty() || userEmail == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email does not exist");
        }
        // End Of Check If Email Is Empty.

        // Get Hashed User Password:
        String hashed_password = userService.checkUserPasswordByEmail(login.getEmail());

        // Validate User Password:
        if(!BCrypt.checkpw(login.getPassword(), hashed_password)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect username or password");
        }

        // Get User Details:
        User user = userService.getUserDetailsByEmail(login.getEmail());

        // Create LoginResponse object:
        LoginResponse response = new LoginResponse(user);

        return ResponseEntity.ok().body(response);
    }*/
}
