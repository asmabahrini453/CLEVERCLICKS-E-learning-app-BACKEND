package com.pfa.project.Entities;

import com.pfa.project.Dto.QuestionsDto;
import com.pfa.project.Dto.QuizDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String questionText ;

    private String option1;

    private String option2;

    private String option3;

    @Column(nullable = false)
    private Long rightAnswerIndex ;


    @CreationTimestamp
    private Instant createdAt ;
    @UpdateTimestamp
    private Instant updatedAt ;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Quiz quiz;

    public QuestionsDto getDto() {
        QuestionsDto question = new QuestionsDto();
        question.setId(id);
        question.setQuestionText(questionText);
        question.setOption1(option1);
        question.setOption2(option2);
        question.setOption3(option3);
        question.setRightAnswerIndex(rightAnswerIndex);

        if (quiz != null) {
            question.setQuizId(quiz.getId());
            question.setQuizTitle(quiz.getQuizTitle());
        }

        return question;
    }
}
