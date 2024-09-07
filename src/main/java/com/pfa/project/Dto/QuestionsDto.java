package com.pfa.project.Dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsDto {
    Long id ;
    String questionText ;
    String option1 ;
    String option2 ;
    String option3 ;
    Long rightAnswerIndex ;
    Long quizId ;
    String quizTitle ;

}
