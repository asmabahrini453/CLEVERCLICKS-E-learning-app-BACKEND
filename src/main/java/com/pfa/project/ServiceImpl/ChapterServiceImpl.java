package com.pfa.project.ServiceImpl;

import com.pfa.project.Dto.*;
import com.pfa.project.Entities.*;
import com.pfa.project.Entities.Enum.Status;
import com.pfa.project.Repository.CategoryRepository;
import com.pfa.project.Repository.ChapterImageRepository;
import com.pfa.project.Repository.ChapterRepository;
import com.pfa.project.Repository.CourseRepository;
import com.pfa.project.Service.ChapterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {
    private final ChapterRepository chapterRepository;
    private final CourseRepository courseRepository;
    private final ChapterImageRepository chapterImageRepository;

    @Override
    public List<GetChapterDto> getAllChapters() {
        List<Chapter> chapters = chapterRepository.findAll();
        return chapters.stream().map(Chapter::getDto).collect(Collectors.toList());
    }

    @Override
    public GetChapterDto getChapterById(Long id) {
        Optional<Chapter> chapterOptional = chapterRepository.findById(id);
        Chapter chapter = chapterOptional.orElseThrow(() -> new EntityNotFoundException("chapter not found with id: " + id));
        return chapter.getDto();
    }
    @Override
    public List<GetChapterDto> getAllChaptersByCourseId(Long courseId) {
        List<Chapter> chapters = chapterRepository.findAllByCourseId(courseId);
        return chapters.stream().map(Chapter::getDto).collect(Collectors.toList());
    }

    @Override
    public RequestChapter createChapter(RequestChapter requestChapter, List<MultipartFile> imageFiles) throws IOException {
        Chapter chapter = new Chapter();
        chapter.setTitle(requestChapter.getTitle());
        chapter.setContents(requestChapter.getContents());
        chapter.setStatus(Status.ACTIVE);

        Course course = courseRepository.findById(requestChapter.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + requestChapter.getCourseId()));
        chapter.setCourse(course);

        Chapter savedChapter = chapterRepository.save(chapter);

        List<ChapterImage> images = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            ChapterImage image = new ChapterImage();
            image.setChapter(savedChapter);
            image.setImage(file.getBytes());
            images.add(chapterImageRepository.save(image));
        }
        savedChapter.setImages(images);
        chapterRepository.save(savedChapter);

        return savedChapter.getDto2();
    }
    @Override
    public RequestChapter updateChapter(Long id, RequestChapter requestChapter, List<MultipartFile> imageFiles) throws IOException {
        Optional<Chapter> chapterOptional = chapterRepository.findById(id);
        Optional<Course> courseOptional = courseRepository.findById(requestChapter.getCourseId());

        if (chapterOptional.isPresent()) {
            Chapter updatedChapter = chapterOptional.get();

            if (requestChapter.getTitle() != null) {
                updatedChapter.setTitle(requestChapter.getTitle());
            }
            if (requestChapter.getContents() != null) {
                updatedChapter.setContents(requestChapter.getContents());
            }

            if (courseOptional.isPresent()) {
                updatedChapter.setCourse(courseOptional.get());
            }

            if (updatedChapter.getImages() != null && !updatedChapter.getImages().isEmpty()) {
                chapterImageRepository.deleteAll(updatedChapter.getImages());
                updatedChapter.setImages(null);
            }

            Chapter savedChapter = chapterRepository.save(updatedChapter);
            if (imageFiles != null && !imageFiles.isEmpty()) {
                List<ChapterImage> newImages = new ArrayList<>();
                for (MultipartFile file : imageFiles) {
                    ChapterImage image = new ChapterImage();
                    image.setImage(file.getBytes());
                    image.setChapter(savedChapter);
                    newImages.add(chapterImageRepository.save(image));
                }
                savedChapter.setImages(newImages);
                chapterRepository.save(savedChapter);
            }

            return savedChapter.getDto2();
        }
        return null;
    }

    @Override
    public List<GetChapterDto> getAllChaptersByTitle(String title) {
        List<Chapter> chapters = chapterRepository.findByTitleContaining(title);
        return chapters.stream().map(Chapter::getDto).collect(Collectors.toList());
    }

    @Override
    public boolean deleteChapter(Long id) {
        Optional<Chapter> optionalChapter = chapterRepository.findById(id);
        if (optionalChapter.isPresent()) {
            chapterRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public GetChapterDto changeStatus(Long id, String status) {
        Optional<Chapter> optional = chapterRepository.findById(id);
        if (optional.isPresent() && (status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Inactive"))) {
            Chapter c = optional.get();
            c.setStatus(Status.valueOf(status.toUpperCase()));
            return chapterRepository.save(c).getDto();
        }
        return null;
    }
}
