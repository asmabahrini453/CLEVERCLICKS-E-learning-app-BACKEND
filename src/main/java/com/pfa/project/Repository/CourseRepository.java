package com.pfa.project.Repository;

import com.pfa.project.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course , Long> {
    List<Course> findByTitleContaining(String title);
}
