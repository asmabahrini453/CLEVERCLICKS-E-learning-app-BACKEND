package com.pfa.project.Repository;

import com.pfa.project.Entities.UserAnswers;
import com.pfa.project.Entities.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
    Optional<UserScore> findByUserId(Long userId);

}
