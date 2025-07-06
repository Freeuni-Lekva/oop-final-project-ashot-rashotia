package com.example.quizwebproject.model.questions;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class PictureResponseQuestion extends Question {

    private String imageUrl;
    private String correctAnswer;
    private int maxPoints;

    public PictureResponseQuestion() {}

    public PictureResponseQuestion(String question, String correctAnswer, String imageUrl, String category, int maxPoints) {
        super(question, correctAnswer, category);
        this.correctAnswer = correctAnswer;
        this.imageUrl = imageUrl;
        this.maxPoints = maxPoints;
        setRawUserAnswer("");
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
        setRawCorrectAnswers(correctAnswer);
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    @Override
    public String getQuestion() {
        // You can format the question and image URL for display if needed
        return getRawQuestion();
    }

    @Override
    public void setUserAnswer(String userAnswer) {
        setRawUserAnswer(userAnswer);
    }

    @Override
    public String getUserAnswer() {
        return getRawUserAnswer();
    }

    @Override
    public String getCategory() {
        return getRawCategory();
    }

    @Override
    public void setCategory(String category) {
        setRawCategory(category);
    }

    @Override
    public List<String> getCorrectAnswers() {
        return List.of(correctAnswer);
    }

    @Override
    public double getResult() {
        String user = getRawUserAnswer();
        if (user == null || user.isBlank()) return 0.0;

        String u = user.trim().replaceAll("\\s+", "").toLowerCase();
        String c = correctAnswer.trim().replaceAll("\\s+", "").toLowerCase();

        return u.equals(c) ? 100.0 : 0.0;
    }

    @Override
    public String toString() {
        return "PictureResponseQuestion {" +
                "id=" + getId() +
                ", question='" + getQuestion() + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", userAnswer='" + getUserAnswer() + '\'' +
                ", result=" + getResult() +
                ", category='" + getCategory() + '\'' +
                ", maxPoints=" + maxPoints +
                '}';
    }
}
