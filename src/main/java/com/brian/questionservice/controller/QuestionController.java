package com.brian.questionservice.controller;

import com.brian.questionservice.model.Question;
import com.brian.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("question")
public class QuestionController {
    //work with http codes instead
    @Autowired
    private QuestionService questionService;
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getQuestions(){
        return  questionService.getAllQuestions();

    }
    @GetMapping("QuestionSpecific/{id}")
    public  ResponseEntity<Optional<Question>> getQuestionByID(@PathVariable int id){
        return questionService.getQuestionByID(id);
    }
    @PostMapping("AddQuestion")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question){
        return  questionService.addQuestion(question);
    }
    @PutMapping("EditQuestion")
    public ResponseEntity<Question> EditQuestion(@RequestBody Question question){
           questionService.getQuestionByID(question.getId());
           return questionService.editQuestion(question);
    }

   @DeleteMapping("delete/{id}")
    public  ResponseEntity<String> DeleteQuestion(@PathVariable int id){
        questionService.deleteQuestion(id);
        return new ResponseEntity<>( "deleted successfully",HttpStatus.OK);
   }
    @GetMapping("searchByCategory/{category}")
    public  ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category){
        return questionService.searchByCategory(category);
    }
}
