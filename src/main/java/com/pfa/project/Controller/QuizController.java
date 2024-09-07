package com.pfa.project.Controller;

import com.pfa.project.Dto.*;
import com.pfa.project.Service.QuizService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pfa/quiz")
@AllArgsConstructor
public class QuizController {
    @Autowired
    QuizService quizService ;

    @GetMapping("")
    public ResponseEntity<List<QuizDto>> getAllQuizzes() {
        List<QuizDto> quizDtos = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizDtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }
    @GetMapping("/chapter/{chapterId}")
    public ResponseEntity<QuizDto> getQuizByChapterId(@PathVariable Long chapterId) {
        QuizDto quizDto = quizService.getQuizByChapterId(chapterId);
        if (quizDto != null) {
            return ResponseEntity.ok(quizDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("")
    public ResponseEntity<QuizDto> addCourse(@Valid QuizDto quizDto
                                                   )  {
        QuizDto createdQuiz = quizService.createQuiz(quizDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdQuiz);
    }

    @PutMapping("{id}")
    public ResponseEntity<QuizDto> updateQuiz(
            @PathVariable Long id,
            @ModelAttribute QuizDto quizDto
    ) {
        try {
            QuizDto updatedquiz = quizService.updateQuiz(id, quizDto);
            return ResponseEntity.ok(updatedquiz);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        boolean deletedQuiz = quizService.deleteQuiz(id);
        if (deletedQuiz) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
   /* @GetMapping("/search/{title}")
    public ResponseEntity<List<QuizDto>> getAllQuizByName(@PathVariable String title) {
        List<QuizDto> quiz = quizService.getAllQuizByTitle(title);
        return ResponseEntity.ok(quiz);
    }*/

}
