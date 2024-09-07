package com.pfa.project.Service;

import com.pfa.project.Entities.ChapterImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ChapterImageService {
    ChapterImage uploadImage(MultipartFile file) throws IOException ;

    ChapterImage getImageById(Long id);

    boolean deleteImage(Long id);
}
