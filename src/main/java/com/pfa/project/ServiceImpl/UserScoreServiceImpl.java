package com.pfa.project.ServiceImpl;

import com.pfa.project.Dto.QuizDto;
import com.pfa.project.Dto.ScoreDto;
import com.pfa.project.Dto.UserAnswersDto;
import com.pfa.project.Dto.UserScoreDto;
import com.pfa.project.Entities.Question;
import com.pfa.project.Entities.Quiz;
import com.pfa.project.Entities.UserAnswers;
import com.pfa.project.Entities.UserScore;
import com.pfa.project.Repository.*;
import com.pfa.project.Service.UserScoreService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserScoreServiceImpl implements UserScoreService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserScoreRepository userScoreRepository;
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    UserAnswersRepository userAnswersRepository;


    @Override
    public int calculateScore(UserScoreDto userScoreDto) {
        Long userId = userScoreDto.getUserId();
        Long quizId = userScoreDto.getQuizId();

        List<UserAnswers> userAnswersList = userAnswersRepository.findByUsersIdAndQuizId(userId, quizId);

        List<Question> correctAnswersList = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found for ID: " + quizId))
                .getQuestion();

        int score = 0;
        for (UserAnswers userAnswers : userAnswersList) {
            Question question = userAnswers.getQuestions();
            int userAnswerIndex = userAnswers.getSelectedOption();
            Long correctAnswerIndex = question.getRightAnswerIndex();

            if (userAnswerIndex == correctAnswerIndex) {
                score++;
            }
        }

        UserScore userScore = new UserScore();
        userScore.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found for ID: " + userId)));
        userScore.setQuiz(quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found for ID: " + quizId)));
        userScore.setScore(score);
        userScoreRepository.save(userScore);

        return score;
    }

    @Override
    public List<ScoreDto> getAllScores() {
        List<UserScore> score = userScoreRepository.findAll();
        return score.stream().map(UserScore::getDto).collect(Collectors.toList());
    }

    @Override
    public boolean deleteScore(Long id) {
        Optional<UserScore> score = userScoreRepository.findById(id);
        if (score.isPresent()) {
            userScoreRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public int displayScoreByUserId(Long userId) {
        UserScore userScore = userScoreRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User score not found for userId: " + userId));

        return userScore.getScore();
    }
}
