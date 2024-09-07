package com.pfa.project.Controller;

import com.pfa.project.Dto.QuizDto;
import com.pfa.project.Dto.ScoreDto;
import com.pfa.project.Dto.UserAnswersDto;
import com.pfa.project.Dto.UserScoreDto;
import com.pfa.project.Service.UserScoreService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pfa/score")
@AllArgsConstructor
public class UserScoreController {
    @Autowired
    UserScoreService userScoreService;

    @PostMapping("/calculate")
    public ResponseEntity<Integer> calculateScore(@RequestBody UserScoreDto userScoreDto) {
        int score = userScoreService.calculateScore(userScoreDto);
        return ResponseEntity.ok(score);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<Integer> displayScoreByUserId(@PathVariable Long userId) {
        int userScore = userScoreService.displayScoreByUserId(userId);
        return ResponseEntity.ok(userScore);
    }
    @GetMapping("")
    public ResponseEntity<List<ScoreDto>> getAllScores() {
        List<ScoreDto> Dtos = userScoreService.getAllScores();
        return ResponseEntity.ok(Dtos);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteScore(@PathVariable Long id) {
        boolean deletedQuiz = userScoreService.deleteScore(id);
        if (deletedQuiz) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}