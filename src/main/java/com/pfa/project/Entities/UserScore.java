package com.pfa.project.Entities;

import com.pfa.project.Dto.ScoreDto;
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
public class UserScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private int score;

    public ScoreDto getDto() {
        ScoreDto u = new ScoreDto();
        u.setId(id);
        u.setScore(score);
        if (user != null) {
            u.setUserId(user.getId());
            u.setName(user.getName());
            u.setEmail(user.getEmail());
        }

        if (quiz != null) {
            u.setQuizId(quiz.getId());
            u.setQuizTitle(quiz.getQuizTitle());
        }

        return u;
    }


}
