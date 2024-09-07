package com.pfa.project.Controller;

import com.pfa.project.Dto.UserAnswersDto;
import com.pfa.project.Service.UserAnswersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pfa/userAnswers")
@AllArgsConstructor
public class UserAnswersController {
    @Autowired
    UserAnswersService userAnswersService ;

    @GetMapping("")
    public ResponseEntity<List<UserAnswersDto>> getAllAnswers() {
        List<UserAnswersDto> Dtos = userAnswersService.getAllAnswers();
        return ResponseEntity.ok(Dtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserAnswersDto> getAnswerById(@PathVariable Long id) {
        return ResponseEntity.ok(userAnswersService.getAnswerById(id));
    }


    @PostMapping("")
    public ResponseEntity<UserAnswersDto> addAnswer(@Valid @RequestBody UserAnswersDto userAnswersDto) {
        userAnswersService.createAnswer(userAnswersDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
