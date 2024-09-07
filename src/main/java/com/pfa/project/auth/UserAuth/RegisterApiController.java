package com.pfa.project.auth.UserAuth;

import com.pfa.project.Entities.User;
import com.pfa.project.Service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/pfa/user")
@RequiredArgsConstructor
public class RegisterApiController {

    @Autowired
    UserService userService;


   /* @PostMapping("/register")
    public ResponseEntity registerNewUser(@RequestParam("name")String name,
                                          @RequestParam("email")String email,
                                          @RequestParam("password")String password){

        if(name.isEmpty()  || email.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Please Complete all Fields", HttpStatus.BAD_REQUEST);
        }

        // Encrypt / Hash  Password:
        String hashed_password = BCrypt.hashpw(password, BCrypt.gensalt());

        // Register New User:
        User result = userService.registerNewUserServiceMethod(name, email, hashed_password);
        if (result == null) {
            return new ResponseEntity<>("Registration failed", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User registration successful", HttpStatus.OK);

    }*/

}
