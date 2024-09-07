package com.pfa.project.Dto;

import com.pfa.project.Entities.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCategory {
     Long id;
     String name ;
     String description;
     Status status ;
     private byte[] byteImg;
     private MultipartFile img;
     private String processedImg;

}
