package com.pfa.project.Entities;

import com.pfa.project.Dto.QuizDto;
import com.pfa.project.Dto.RequestCourse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String quizTitle ;
    private Boolean status ;
    @CreationTimestamp
    private Instant createdAt ;
    @UpdateTimestamp
    private Instant updatedAt ;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Chapter chapter;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Question> question ;

    public QuizDto getDto() {
        QuizDto quiz = new QuizDto();
        quiz.setId(id);
        quiz.setQuizTitle(quizTitle);
        quiz.setStatus(true);

        if (chapter != null) {
            quiz.setChapterId(chapter.getId());
            quiz.setChapterTitle(chapter.getTitle());
        }

        return quiz;
    }

}
