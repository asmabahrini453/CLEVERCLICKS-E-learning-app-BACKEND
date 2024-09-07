package com.pfa.project.Service;

import com.pfa.project.Dto.RequestCourse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface CourseService {
    List<RequestCourse> getAllCourses();
    RequestCourse getCourseById(Long id);
    RequestCourse createCourse(RequestCourse requestCourse, MultipartFile imageFile) throws IOException;
    RequestCourse updateCourse(Long id, RequestCourse requestCourse) throws IOException;
    boolean deleteCourse(Long id);
    List<RequestCourse> getAllCoursesByName(String title);


}
