package com.pfa.project.ServiceImpl;

import com.pfa.project.Dto.*;
import com.pfa.project.Entities.Category;
import com.pfa.project.Entities.Chapter;
import com.pfa.project.Entities.Course;
import com.pfa.project.Entities.Quiz;
import com.pfa.project.Repository.ChapterRepository;
import com.pfa.project.Repository.CourseRepository;
import com.pfa.project.Repository.QuizRepository;
import com.pfa.project.Service.QuizService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    QuizRepository quizRepository;

    @Override
    public QuizDto createQuiz(QuizDto quizDto)  {

        Quiz quiz = new Quiz();
        quiz.setQuizTitle(quizDto.getQuizTitle());
        quiz.setStatus(quizDto.getStatus());
        Chapter chapter  = chapterRepository.findById(quizDto.getChapterId()).orElseThrow();
        quiz.setChapter(chapter);

        return quizRepository.save(quiz).getDto();
    }
    @Override
    public List<QuizDto> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream().map(Quiz::getDto).collect(Collectors.toList());
    }

    @Override
    public QuizDto getQuizById(Long id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        Quiz quiz = quizOptional.orElseThrow(() -> new EntityNotFoundException("quiz not found with id: " + id));
        return quiz.getDto();
    }
    @Override
    public QuizDto getQuizByChapterId(Long chapterId) {
        Quiz quiz = quizRepository.findByChapterId(chapterId);
        if (quiz != null) {
            return quiz.getDto();
        }
        return null;

    }

   /* @Override
    public List<QuizDto> getAllQuizByTitle(String title) {
        List<Quiz> quiz = quizRepository.findByTitleContaining(title);
        return quiz.stream().map(Quiz::getDto).collect(Collectors.toList());
    }*/

    @Override
    public QuizDto updateQuiz(Long id, QuizDto quizDto)  {
        Optional<Quiz> quizOptional = quizRepository.findById(id);

        if (quizOptional.isPresent()) {
            Quiz updatedQuiz = quizOptional.get();

            if (quizDto.getQuizTitle() != null){
                updatedQuiz.setQuizTitle(quizDto.getQuizTitle());

            }

            if (quizDto.getStatus() != null){
                updatedQuiz.setStatus(quizDto.getStatus());

            }
            if (quizDto.getChapterId() != null) {
                Optional<Chapter> chapterOptional = chapterRepository.findById(quizDto.getChapterId());
                if (chapterOptional.isPresent()) {
                    updatedQuiz.setChapter(chapterOptional.get());
                } else {
                    throw new EntityNotFoundException("Chapter not found for ID: " + quizDto.getChapterId());
                }
            }

            Quiz savedQuiz = quizRepository.save(updatedQuiz);
            return savedQuiz.getDto();
        } else {
            throw new EntityNotFoundException("quiz not found");
        }
    }
    @Override
    public boolean deleteQuiz(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isPresent()) {
            quizRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
