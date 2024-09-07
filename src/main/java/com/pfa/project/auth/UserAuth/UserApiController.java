package com.pfa.project.auth.UserAuth;

import com.pfa.project.Dto.*;
import com.pfa.project.Service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/pfa/user")
public class UserApiController {
    @Autowired
    private  UserService userService;

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllusers() {
        List<UserDto> user = userService.getAllUsers();
        return ResponseEntity.ok(user);
    }


    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDetailsById(id));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Object> updateProfile(
            @PathVariable Long id,
            @RequestBody @Valid RequestUserUpdate requestUserUpdate){
        userService.updateProfile(id ,requestUserUpdate) ;
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap("message:" , "user profile updated successfully!! ")
        );
    }

    @PutMapping("/{id}/profile-picture")
    public ResponseEntity<Object> updateProfilePicture(
            @PathVariable Long id,
            @RequestParam("profilePicture") MultipartFile profilePicture) {
        userService.updateProfilePicture(id, profilePicture);
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap("message", "Profile picture updated successfully")
        );
    }

  /*  @PutMapping(value = "{id}")
    public ResponseEntity<RequestUserUpdate> updateUser(
            @PathVariable Long id,
            @ModelAttribute RequestUserUpdate requestUserUpdate
    ) {
        try {
            RequestUserUpdate updated = userService.updateUser(id, requestUserUpdate);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/


    @PutMapping(value = "/password/{id}")
    public ResponseEntity<Object> updatePassword(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid RequestChangePassword requestChangePassword
    ){
        userService.changePassword(id, requestChangePassword);
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap("message: ", "password updated successfully!! ")
        );
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<UserDto>> getAllUserByName(@PathVariable String name) {
        List<UserDto> u = userService.getAllUserByName(name);
        return ResponseEntity.ok(u);
    }

    @GetMapping("/{id}/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id,@PathVariable String status){
        UserDto dto = userService.changeStatus(id,status);
        if(dto == null){
            return new ResponseEntity<>("Something went wrong!" , HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
