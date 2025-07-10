package com.example.quizwebproject.repos;

import com.example.quizwebproject.model.questions.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<Question, Long> {
}
