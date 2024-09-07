package com.pfa.project.Repository;

import com.pfa.project.Entities.UserAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswersRepository extends JpaRepository<UserAnswers, Long> {
    List<UserAnswers> findByUsersIdAndQuizId(Long userId, Long quizId);
}
