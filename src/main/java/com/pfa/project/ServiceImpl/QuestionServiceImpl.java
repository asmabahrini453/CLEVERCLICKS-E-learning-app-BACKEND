package com.pfa.project.ServiceImpl;

import com.pfa.project.Dto.QuestionsDto;
import com.pfa.project.Entities.Chapter;
import com.pfa.project.Entities.Question;
import com.pfa.project.Entities.Quiz;
import com.pfa.project.Repository.QuestionRepository;
import com.pfa.project.Repository.QuizRepository;
import com.pfa.project.Service.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    QuizRepository quizRepository;


    @Override
    public QuestionsDto createQuestion(QuestionsDto questionsDto) {
        Question question = new Question();
        question.setQuestionText(questionsDto.getQuestionText());
        question.setOption1(questionsDto.getOption1());
        question.setOption2(questionsDto.getOption2());
        question.setOption3(questionsDto.getOption3());
        question.setRightAnswerIndex(questionsDto.getRightAnswerIndex());

        Quiz quiz  = quizRepository.findById(questionsDto.getQuizId()).orElseThrow();
        question.setQuiz(quiz);

        return questionRepository.save(question).getDto();
    }

    @Override
    public List<QuestionsDto> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(Question::getDto).collect(Collectors.toList());
    }

    @Override
    public QuestionsDto getQuestionById(Long id) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        Question question = questionOptional.orElseThrow(() -> new EntityNotFoundException("question not found with id: " + id));
        return question.getDto();
    }
    @Override
    public List<QuestionsDto> getAllQuestionsByQuizId(Long quizId) {
        List<Question> questions = questionRepository.findByQuizId(quizId);
        return questions.stream().map(Question::getDto).collect(Collectors.toList());
    }
    @Override
    public QuestionsDto updateQuestion(Long id, QuestionsDto questionsDto) {
        Optional<Question> questionOptional = questionRepository.findById(id);

        if (questionOptional.isPresent()) {
            Question updatedQuestion = questionOptional.get();

            if (questionsDto.getQuestionText() != null){
                updatedQuestion.setQuestionText(questionsDto.getQuestionText());

            }


            if (questionsDto.getOption1() != null){
                updatedQuestion.setOption1(questionsDto.getOption1());

            }
            if (questionsDto.getOption2() != null){
                updatedQuestion.setOption2(questionsDto.getOption2());

            }
            if (questionsDto.getOption3() != null){
                updatedQuestion.setOption3(questionsDto.getOption3());

            }
            if (questionsDto.getRightAnswerIndex() != null){
                updatedQuestion.setRightAnswerIndex(questionsDto.getRightAnswerIndex());

            }
            if (questionsDto.getQuizId() != null) {
                Optional<Quiz> quiz = quizRepository.findById(questionsDto.getQuizId());
                if (quiz.isPresent()) {
                    updatedQuestion.setQuiz(quiz.get());
                } else {
                    throw new EntityNotFoundException("quiz not found for ID: " + questionsDto.getQuizId());
                }
            }

            Question savedQuestion = questionRepository.save(updatedQuestion);
            return savedQuestion.getDto();
        } else {
            throw new EntityNotFoundException("question not found");
        }    }

    @Override
    public boolean deleteQuestion(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            questionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
