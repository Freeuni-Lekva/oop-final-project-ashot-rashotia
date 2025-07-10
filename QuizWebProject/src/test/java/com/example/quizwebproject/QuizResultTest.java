package com.example.quizwebproject;


import com.example.quizwebproject.repos.QuizResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class QuizResultTest {

    @Autowired
    private QuizResultRepo quizRepo;

}
