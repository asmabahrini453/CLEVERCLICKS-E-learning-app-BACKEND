package com.pfa.project.Controller;

import com.pfa.project.Dto.RequestCourse;
import com.pfa.project.Service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/pfa/course")
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("")
    public ResponseEntity<List<RequestCourse>> getAllCourses() {
        List<RequestCourse> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("{id}")
    public ResponseEntity<RequestCourse> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping("")
    public ResponseEntity<RequestCourse> addCourse(@Valid RequestCourse requestCourse,
                                                   @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        RequestCourse createdCourse = courseService.createCourse(requestCourse, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdCourse);
    }

    @PutMapping("{id}")
    public ResponseEntity<RequestCourse> updateCourse(
            @PathVariable Long id,
            @ModelAttribute RequestCourse requestCourse
    ) {
        try {
            RequestCourse updatedCourse = courseService.updateCourse(id, requestCourse);
            return ResponseEntity.ok(updatedCourse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        boolean deletedCourse = courseService.deleteCourse(id);
        if (deletedCourse) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<RequestCourse>> getAllCourseByName(@PathVariable String title) {
        List<RequestCourse> courses = courseService.getAllCoursesByName(title);
        return ResponseEntity.ok(courses);
    }
}
