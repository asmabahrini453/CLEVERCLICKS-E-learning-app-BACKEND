package com.pfa.project.Dto;

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
public class RequestQuestions {
     Long id ;
     String questionText ;
     String answerOptions ;
     Long rightAnswerIndex ;
     Boolean status ;
     Long quizId ;
}
