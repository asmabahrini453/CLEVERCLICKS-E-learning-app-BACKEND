package com.pfa.project.Dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    Long id ;
    String quizTitle ;
    Boolean status ;
    Long chapterId ;
    String chapterTitle;

}
