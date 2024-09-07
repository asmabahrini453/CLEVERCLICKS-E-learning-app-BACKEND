package com.pfa.project.Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDto {
    Long id;
    Long quizId;
    String quizTitle;
    Long userId;
    String name ;
    String email ;

    private int score;

}
