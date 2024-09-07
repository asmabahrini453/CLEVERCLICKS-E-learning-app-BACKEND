package com.pfa.project.Controller;

import com.pfa.project.Dto.GetChapterDto;
import com.pfa.project.Dto.RequestChapter;
import com.pfa.project.Dto.RequestCourse;
import com.pfa.project.Dto.UserDto;
import com.pfa.project.Entities.Chapter;
import com.pfa.project.Service.ChapterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/pfa/chapter")
@RequiredArgsConstructor
public class  ChapterController {
    private final ChapterService chapterService;

    @GetMapping("")
    public ResponseEntity<List<GetChapterDto>> getAllChapters() {
        List<GetChapterDto> chapters = chapterService.getAllChapters();
        return ResponseEntity.ok(chapters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetChapterDto> getChapterById(@PathVariable Long id) {
        GetChapterDto chapter = chapterService.getChapterById(id);
        return ResponseEntity.ok(chapter);
    }
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<GetChapterDto>> getAllChaptersByCourseId(@PathVariable Long courseId) {
        List<GetChapterDto> chapters = chapterService.getAllChaptersByCourseId(courseId);
        return ResponseEntity.ok(chapters);
    }

    @PostMapping("")
    public ResponseEntity<RequestChapter> createChapter(@Valid RequestChapter requestChapter,
                                                        @RequestPart("images") List<MultipartFile> images) throws IOException {
        RequestChapter createdChapter = chapterService.createChapter(requestChapter, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChapter);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RequestChapter> updateChapter(
            @PathVariable Long id, @RequestParam("images") List<MultipartFile> files,
            @ModelAttribute RequestChapter requestChapter) {
        try {
            RequestChapter updatedChapter = chapterService.updateChapter(id, requestChapter, files);
            if (updatedChapter != null) {
                return ResponseEntity.ok(updatedChapter);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChapter(@PathVariable Long id) {
        boolean deleted = chapterService.deleteChapter(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/search/{title}")
    public ResponseEntity<List<GetChapterDto>> getAllChaptersByTitle(@PathVariable String title) {
        List<GetChapterDto> c = chapterService.getAllChaptersByTitle(title);
        return ResponseEntity.ok(c);
    }

    @GetMapping("/{id}/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id,@PathVariable String status){
        GetChapterDto dto = chapterService.changeStatus(id,status);
        if(dto == null){
            return new ResponseEntity<>("error" , HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

}
