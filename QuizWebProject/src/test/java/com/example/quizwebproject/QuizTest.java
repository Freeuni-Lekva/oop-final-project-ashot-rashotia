package com.example.quizwebproject;


import com.example.quizwebproject.model.questions.Question;
import com.example.quizwebproject.model.questions.TestQuestion;
import com.example.quizwebproject.model.quizes.Quiz;
import com.example.quizwebproject.model.users.User;
import com.example.quizwebproject.repos.QuizRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class QuizTest {

    @Autowired
    private QuizRepo quizRepo;

    private Quiz quiz;

    @BeforeEach
    void setUp() {
        String description = "Quiz Test";
        List<Question> questions = new ArrayList<>();
        User author = new User("ika", "cuxo");
        String type = "Difficult";

        List<String> correctAnswers = new ArrayList<>();
        correctAnswers.add("Tbilisi");
        Question q1 = new TestQuestion(
                "What is the capital of Georgia?",
                correctAnswers,
                "Geography",
                1.0,
                "Mtskheta,Tbilisi,Paris,Rome"
        );

        correctAnswers.remove(0);
        correctAnswers.add("4");
        Question q2 = new TestQuestion(
                "What is 2+2 equal to?",
                correctAnswers,
                "Maths",
                1.0,
                "1,2,3,4"
        );

        questions.add(q1);
        questions.add(q2);

        quiz = new Quiz(
                false,
                false,
                false,
                false,
                description,
                questions,
                author,
                type
        );
    }

    @Test
    public void testQuizDescription(){
        String newDescription = "Easy";
        assertNotEquals(newDescription, quiz.getDescription());
        assertEquals("Difficult", quiz.getDescription());
        quiz.setDescription(newDescription);
        assertEquals(newDescription, quiz.getDescription());
        assertNotEquals("Difficult", quiz.getDescription());
    }

    @Test
    public void testQuizRandomize(){
        assertFalse(quiz.isRandomize());
        quiz.setRandomize(true);
        assertTrue(quiz.isRandomize());
    }

    @Test
    public void testQuizQuestions(){
        List<Question> dummy = quiz.getQuestions();
        assertEquals("Tbilisi", dummy.get(0).getCorrectAnswers().get(0));
        assertEquals("Geography", dummy.get(0).getCategory());
        assertEquals("What is the capital of Georgia?", dummy.get(0).getQuestion());
        assertEquals("4", dummy.get(1).getCorrectAnswers().get(0));
        assertEquals("Maths", dummy.get(1).getCategory());
        assertEquals("What is 2+2 equal to?", dummy.get(1).getQuestion());
    }

}
