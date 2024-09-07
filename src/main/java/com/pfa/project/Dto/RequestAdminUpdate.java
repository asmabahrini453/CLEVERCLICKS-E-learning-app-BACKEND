package com.pfa.project.Dto;

import com.pfa.project.Entities.Enum.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestAdminUpdate {
    Long id ;
    String username ;
    String email ;
    Boolean status;
    private String firstName;
    private String lastName;
    private String phone;
    private String description;
    private byte[] byteImg;
    private MultipartFile img;
    private String processedImg;
}
