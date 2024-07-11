package com.brian.questionservice.service;

import com.brian.questionservice.model.Question;
import com.brian.questionservice.model.QuestionWrapper;
import com.brian.questionservice.model.Response;
import com.brian.questionservice.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepo questionRepo;

    // putting response entity here to handle exceptions
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Optional<Question>> getQuestionByID(int id) {
        try {
            return new ResponseEntity<>(questionRepo.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<Question> addQuestion(Question question) {
        try {
            return new ResponseEntity<>(questionRepo.save(question), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Question> editQuestion(Question question) {
        try {
            return new ResponseEntity<>(questionRepo.save(question), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public void deleteQuestion(int id) {
        try {
            questionRepo.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Question>> searchByCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepo.findQuestionByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numQuestions) {
      List<Integer> questions = questionRepo.findRandomQuestionByCategory(categoryName,numQuestions);
      return new ResponseEntity<>(questions, HttpStatus.OK);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer i: questionIds){
            questions.add(questionRepo.findById(i).orElseThrow());
        }
        for(Question question: questions){
            QuestionWrapper wrapper1 = new QuestionWrapper();
            wrapper1.setId(question.getId());
            wrapper1.setQuestionTitle(question.getQuestionTitle());
            wrapper1.setOption1(question.getOption1());
            wrapper1.setOption2(question.getOption2());
            wrapper1.setOption3(question.getOption3());
            wrapper1.setOption4(question.getOption4());
            wrappers.add(wrapper1);

        }
        return new ResponseEntity<>(wrappers, HttpStatus.OK);

    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int correct = 0;

        for(Response response1: responses){
            Question questions = questionRepo.findById(response1.getId()).get();
            if(response1.getResponse().equals(questions.getRightAnswer())){
                correct++;
            }


        }
        return  new ResponseEntity<>(correct, HttpStatus.OK);

        //question service will now be used as a quiz service
        //running multiple instances of this question Service - this particular instance runs on port 8080
        // selects which instance it will use also


    }
}
