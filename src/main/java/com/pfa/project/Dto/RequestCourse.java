package com.pfa.project.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCourse {
    private Long id;
    private String title;
    private String description;
    private String rate;
    private Long price;
    private Boolean status;
    private Long categoryId;
    private Long adminId;
    private String categoryName;

    private byte[] byteImg;
    private MultipartFile img;
    private String processedImg;

}
