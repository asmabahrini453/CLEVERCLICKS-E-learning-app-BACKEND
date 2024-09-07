package com.pfa.project.Entities;

import com.pfa.project.Dto.QuizDto;
import com.pfa.project.Dto.UserAnswersDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question questions;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @Column(nullable = false)
    private int selectedOption;

    public UserAnswersDto getDto() {
        UserAnswersDto u = new UserAnswersDto();
        u.setId(id);
        u.setSelectedOption(selectedOption);
        if (users != null) {
            u.setUserId(users.getId());
        }
        if (questions != null) {
            u.setQuestionId(questions.getId());
        }
        if (quiz != null) {
            u.setQuizId(quiz.getId());
        }

        return u;
    }


}
