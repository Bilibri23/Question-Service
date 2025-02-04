package com.brian.questionservice.controller;

import com.brian.questionservice.model.Question;
import com.brian.questionservice.model.QuestionWrapper;
import com.brian.questionservice.model.Response;
import com.brian.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    @Autowired
    private Environment environment;


    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getQuestions(){
        return  questionService.getAllQuestions();

    }
    @GetMapping("QuestionSpecific/{id}")
    public  ResponseEntity<Optional<Question>> getQuestionByID(@PathVariable int id){
        System.out.println(environment.getProperty("local.server.port"));
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
    // if question service needs a quiz , it's the job of the question service to
    //generate it through http request
    //each microservice will have separate database
    // each service is independent from the other
    //no tight coupling
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam int numQuestions){
        return questionService.getQuestionsForQuiz(categoryName,numQuestions);
    }
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromId(questionIds);
    }
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
}
