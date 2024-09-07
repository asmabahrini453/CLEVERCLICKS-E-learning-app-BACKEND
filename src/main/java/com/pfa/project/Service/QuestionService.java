package com.pfa.project.Service;

import com.pfa.project.Dto.QuestionsDto;
import com.pfa.project.Dto.QuizDto;

import java.util.List;

public interface QuestionService {
    QuestionsDto createQuestion(QuestionsDto questionsDto);
    List<QuestionsDto> getAllQuestions();
    QuestionsDto getQuestionById(Long id);
    QuestionsDto updateQuestion(Long id, QuestionsDto questionsDto);
    boolean deleteQuestion(Long id) ;
    List<QuestionsDto> getAllQuestionsByQuizId(Long quizId);


}
