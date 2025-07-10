package com.example.quizwebproject;


import com.example.quizwebproject.repos.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class QuizTest {

    @Autowired
    private QuizRepo quizRepo;

}
