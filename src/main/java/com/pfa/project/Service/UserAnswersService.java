package com.pfa.project.Service;

import com.pfa.project.Dto.QuizDto;
import com.pfa.project.Dto.UserAnswersDto;

import java.util.List;

public interface UserAnswersService {
    List<UserAnswersDto> getAllAnswers();
    UserAnswersDto getAnswerById(Long id);
    void createAnswer(UserAnswersDto userAnswersDto);
}
