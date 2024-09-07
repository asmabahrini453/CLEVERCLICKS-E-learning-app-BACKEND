package com.pfa.project.Dto;

import com.pfa.project.Entities.Chapter;
import com.pfa.project.Entities.UserAnswers;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswersDto {
     Long id ;
     Long quizId;
     Long userId;
     Long questionId;
     Integer selectedOption;

     public static UserAnswersDto makeAnswer(UserAnswers answers) {
          return UserAnswersDto.builder()
                  .id(answers.getId())
                  .quizId(answers.getQuiz().getId())
                  .userId(answers.getUsers().getId())
                  .questionId(answers.getQuestions().getId())
                  .selectedOption(answers.getSelectedOption())
                  .build();
     }


}
