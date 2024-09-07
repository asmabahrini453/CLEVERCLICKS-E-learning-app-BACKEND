package com.pfa.project.Controller;

import com.pfa.project.Dto.*;
import com.pfa.project.Service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/pfa/admin")
@AllArgsConstructor
public class AdminController {
    @Autowired
    private AdminService adminService;


    @GetMapping("")
    public ResponseEntity<List<RequestAdmin>> getAllAdmins() {
        List<RequestAdmin> admin = adminService.getAllAdmins();
        return ResponseEntity.ok(admin);
    }

    @GetMapping("{id}")
    public ResponseEntity<RequestAdmin> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    @PostMapping("")
    public ResponseEntity<RequestAdmin> addAdmin(
            @Valid RequestAdmin requestAdmin,
            @RequestParam("imageFile") MultipartFile imageFile)
            throws IOException {
        RequestAdmin createdAdmin = adminService.createAdmin(requestAdmin, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdAdmin);
    }

    @PutMapping("{id}")
    public ResponseEntity<RequestAdminUpdate> updateAdmin(
            @PathVariable Long id,
            @ModelAttribute RequestAdminUpdate requestAdminUpdate
    ) {
        try {
            RequestAdminUpdate updatedAdmin = adminService.updateAdmin(id, requestAdminUpdate);
            return ResponseEntity.ok(updatedAdmin);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PutMapping(value = "password/{id}")
    public ResponseEntity<Object> updatePassword(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid RequestChangePassword requestChangePassword
    ){
        adminService.changePassword(id, requestChangePassword);
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap("message: ", "password updated successfully!! ")
        );
    }

}
