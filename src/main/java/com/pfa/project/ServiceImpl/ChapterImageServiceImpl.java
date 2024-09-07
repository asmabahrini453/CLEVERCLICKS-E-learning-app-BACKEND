package com.pfa.project.ServiceImpl;

import com.pfa.project.Entities.ChapterImage;
import com.pfa.project.Repository.ChapterImageRepository;
import com.pfa.project.Service.ChapterImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChapterImageServiceImpl implements ChapterImageService {
    private final ChapterImageRepository imageRepository;
    @Override
    public ChapterImage uploadImage(MultipartFile file) throws IOException {
        ChapterImage image = new ChapterImage();
        image.setImage(file.getBytes());
        return imageRepository.save(image);
    }

    @Override
    public ChapterImage getImageById(Long id) {
        Optional<ChapterImage> imageOptional = imageRepository.findById(id);
        return imageOptional.orElse(null);
    }

    @Override
    public boolean deleteImage(Long id) {
        Optional<ChapterImage> imageOptional = imageRepository.findById(id);
        if (imageOptional.isPresent()) {
            imageRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
