package com.pfa.project.Repository;

import com.pfa.project.Entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback ,Long> {
}
