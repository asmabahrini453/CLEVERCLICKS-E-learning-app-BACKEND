package com.pfa.project.Service;

import com.pfa.project.Dto.GetChapterDto;
import com.pfa.project.Dto.RequestChapter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ChapterService {
    List<GetChapterDto> getAllChapters();
    GetChapterDto getChapterById(Long id);
    RequestChapter createChapter(RequestChapter requestChapter, List<MultipartFile> imageFiles) throws IOException;
    RequestChapter updateChapter(Long id, RequestChapter requestChapter, List<MultipartFile> imageFiles) throws IOException;
    boolean deleteChapter(Long id) ;
    List<GetChapterDto> getAllChaptersByCourseId(Long courseId);
    List<GetChapterDto> getAllChaptersByTitle(String title);
    GetChapterDto changeStatus(Long id, String status);
}
