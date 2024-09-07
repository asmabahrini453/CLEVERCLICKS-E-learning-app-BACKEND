package com.pfa.project.Dto;

import com.pfa.project.Entities.Enum.Role;
import com.pfa.project.Entities.Enum.Status;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
     Long id;
     String name ;
     String email;
     String password;
     Role role;
     Status status;
     Instant createdAt;
     Instant updatedAt;
     private byte[] byteImg;
     private MultipartFile img;
     private String processedImg;
     // Getter method for username
     public String getUsername() {
          return email; // Assuming email is used as the username
     }
}
