package com.pfa.project.Service;

import com.pfa.project.Dto.*;
import com.pfa.project.Entities.Chapter;
import com.pfa.project.Entities.Quiz;

import java.util.List;

public interface QuizService {
    QuizDto createQuiz(QuizDto quizDto);
    List<QuizDto> getAllQuizzes();
    QuizDto getQuizById(Long id);
    QuizDto updateQuiz(Long id, QuizDto quizDto);
    boolean deleteQuiz(Long id) ;
    QuizDto getQuizByChapterId(Long chapterId);
  //  List<QuizDto> getAllQuizByTitle(String title);

}
