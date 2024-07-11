package com.brian.questionservice.repository;

import com.brian.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepo  extends JpaRepository<Question, Integer> {
    List<Question> findQuestionByCategory(String category);

    @Query(value = "select q.id from question q where q.category=:category ORDER BY  RANDOM() LIMIT :numQuestions",  nativeQuery = true)
    List<Integer> findRandomQuestionByCategory(String category, int numQuestions);
}
