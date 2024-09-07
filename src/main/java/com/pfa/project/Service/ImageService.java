package com.pfa.project.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
     String uploadImage(MultipartFile imageFile) throws IOException;
    byte[] downloadImage(String imageName);
}
