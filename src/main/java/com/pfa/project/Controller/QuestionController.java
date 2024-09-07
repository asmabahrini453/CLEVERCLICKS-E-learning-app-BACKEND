package com.pfa.project.Controller;

import com.pfa.project.Dto.QuestionsDto;
import com.pfa.project.Dto.QuizDto;
import com.pfa.project.Service.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pfa/question")
@AllArgsConstructor
public class QuestionController {
    @Autowired
    QuestionService questionService ;

    @GetMapping("")
    public ResponseEntity<List<QuestionsDto>> getAllQuestions() {
        List<QuestionsDto> questionsDtos = questionService.getAllQuestions();
        return ResponseEntity.ok(questionsDtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<QuestionsDto> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuestionsDto>> getAllQuestionsByQuizId(@PathVariable Long quizId) {
        List<QuestionsDto> questionsDtos = questionService.getAllQuestionsByQuizId(quizId);
        if (questionsDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(questionsDtos);
    }
    @PostMapping("")
    public ResponseEntity<QuestionsDto> addQuestion(@Valid QuestionsDto questionsDto
    )  {
        QuestionsDto createdQuestion = questionService.createQuestion(questionsDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdQuestion);
    }

    @PutMapping("{id}")
    public ResponseEntity<QuestionsDto> updateQuestion(
            @PathVariable Long id,
            @ModelAttribute QuestionsDto questionsDto
    ) {
        try {
            QuestionsDto updatedquestion = questionService.updateQuestion(id, questionsDto);
            return ResponseEntity.ok(updatedquestion);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        boolean deletedQuestion = questionService.deleteQuestion(id);
        if (deletedQuestion) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
