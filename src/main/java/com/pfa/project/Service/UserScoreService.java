package com.pfa.project.Service;

import com.pfa.project.Dto.ScoreDto;
import com.pfa.project.Dto.UserAnswersDto;
import com.pfa.project.Dto.UserScoreDto;

import java.util.List;

public interface UserScoreService {
    int calculateScore(UserScoreDto userScoreDto);
    int displayScoreByUserId(Long userId);
    List<ScoreDto> getAllScores() ;
    boolean deleteScore(Long id) ;

}
