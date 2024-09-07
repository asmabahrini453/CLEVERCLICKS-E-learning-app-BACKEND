package com.pfa.project.ServiceImpl;

import com.pfa.project.Dto.UserAnswersDto;
import com.pfa.project.Entities.*;
import com.pfa.project.Repository.*;
import com.pfa.project.Service.UserAnswersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAnswersServiceImpl implements UserAnswersService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    UserAnswersRepository userAnswersRepository;

    @Override
    public List<UserAnswersDto> getAllAnswers() {
        List<UserAnswers> answers = userAnswersRepository.findAll();
        return answers.stream().map(UserAnswers::getDto).collect(Collectors.toList());
    }

    @Override
    public UserAnswersDto getAnswerById(Long id) {
        Optional<UserAnswers> userAnswers = userAnswersRepository.findById(id);
        return UserAnswersDto.makeAnswer(userAnswers.get());
    }


    @Override
    public void createAnswer(UserAnswersDto userAnswersDto) {
        User user = userRepository.findById(userAnswersDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Question question = questionRepository.findById(userAnswersDto.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));
        Quiz quiz = quizRepository.findById(userAnswersDto.getQuizId())
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        UserAnswers answers = UserAnswers.builder()
                .selectedOption(userAnswersDto.getSelectedOption())
                .users(user)
                .questions(question)
                .quiz(quiz)
                .build();
        userAnswersRepository.save(answers);
    }


}
