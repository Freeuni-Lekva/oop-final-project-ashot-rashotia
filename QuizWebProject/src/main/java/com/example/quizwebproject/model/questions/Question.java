package com.example.quizwebproject.model.questions;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String question;

    @Lob
    private String correctAnswers;

    private String userAnswer;

    private String category;

    public Question() {
        // Default
    }

    public Question(String question, String correctAnswers, String category) {
        this.question = question;
        this.correctAnswers = correctAnswers;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getRawQuestion() {
        return question;
    }

    public void setRawQuestion(String question) {
        this.question = question;
    }

    public String getRawCorrectAnswers() {
        return correctAnswers;
    }

    public void setRawCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setRawUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getRawUserAnswer() {
        return userAnswer;
    }

    public String getRawCategory() {
        return category;
    }

    public void setRawCategory(String category) {
        this.category = category;
    }

    public abstract String getQuestion();
    public abstract void setUserAnswer(String userAnswer);
    public abstract List<String> getCorrectAnswers();
    public abstract double getResult();
    public abstract String getUserAnswer();
    public abstract String getCategory();
    public abstract void setCategory(String category);
}
