package com.pfa.project.Repository;

import com.pfa.project.Entities.Course;
import com.pfa.project.Entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Quiz findByChapterId(Long chapterId);
   // List<Quiz> findByTitleContaining(String title);

}
