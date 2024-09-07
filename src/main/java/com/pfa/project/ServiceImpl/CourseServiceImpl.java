package com.pfa.project.ServiceImpl;

import com.pfa.project.Dto.RequestCourse;
import com.pfa.project.Entities.Admin;
import com.pfa.project.Entities.Category;
import com.pfa.project.Entities.Course;
import com.pfa.project.Repository.AdminRepository;
import com.pfa.project.Repository.CategoryRepository;
import com.pfa.project.Repository.CourseRepository;
import com.pfa.project.Service.CourseService;
import com.pfa.project.Service.ImageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final AdminRepository adminRepository ;
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<RequestCourse> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(Course::getDto).collect(Collectors.toList());
    }

    @Override
    public RequestCourse getCourseById(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        Course course = courseOptional.orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
        return course.getDto();
    }


    @Override
    public RequestCourse createCourse(RequestCourse requestCourse, MultipartFile imageFile) throws IOException {

        Course course = new Course();
        course.setTitle(requestCourse.getTitle());
        course.setDescription(requestCourse.getDescription());
        course.setRate(requestCourse.getRate());
        course.setPrice(requestCourse.getPrice());
        Category category  = categoryRepository.findById(requestCourse.getCategoryId()).orElseThrow();
        course.setCategory(category);

        Admin admin = adminRepository.findById(1L).orElseThrow();
        course.setAdmin(admin);

        course.setStatus(requestCourse.getStatus());
        course.setImg(imageFile.getBytes());



        return courseRepository.save(course).getDto();
    }

    @Override
    public RequestCourse updateCourse(Long id, RequestCourse requestCourse) throws IOException {
        Optional<Course> courseOptional = courseRepository.findById(id);
        Optional<Category> categoryOptional = categoryRepository.findById(requestCourse.getCategoryId());

        if (courseOptional.isPresent() && categoryOptional.isPresent()) {
            Course updatedCourse = courseOptional.get();

            if (requestCourse.getTitle() != null){
                updatedCourse.setTitle(requestCourse.getTitle());

            }
            if (requestCourse.getDescription() != null){
                updatedCourse.setDescription(requestCourse.getDescription());

            }
            if (requestCourse.getRate() != null){
                updatedCourse.setRate(requestCourse.getRate());

            }
            if (requestCourse.getStatus() != null){
                updatedCourse.setStatus(requestCourse.getStatus());

            }
            if (requestCourse.getPrice() != null){
                updatedCourse.setPrice(requestCourse.getPrice());

            }
            Admin admin = adminRepository.findById(1L).orElseThrow();

            updatedCourse.setAdmin(admin);

            if (requestCourse.getImg() != null) {
                updatedCourse.setImg(requestCourse.getImg().getBytes());
            }

            Course savedCourse = courseRepository.save(updatedCourse);
            return savedCourse.getDto();
        } else {
            throw new EntityNotFoundException("Course or Category not found");
        }
    }

    @Override
    public boolean deleteCourse(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

   @Override
    public List<RequestCourse> getAllCoursesByName(String title) {
        List<Course> courses = courseRepository.findByTitleContaining(title);
        return courses.stream().map(Course::getDto).collect(Collectors.toList());
    }
}
