package com.example.quizwebproject.model.questions;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

import java.util.List;

@Entity
public class GradedQuestion extends Question {

    private double score; // max 100
    private boolean graded;
    private String feedback;

    public GradedQuestion() {}



    public GradedQuestion(String question, String category, Double maxPoints) {
        super(question, "", category, maxPoints);
        this.graded = false;
        this.score = 0.0;
        this.feedback = "";
        setRawUserAnswer("");
    }

    @Override
    public void setMaxPoints(Double maxPoints) {
        setRawMaxPoints(maxPoints);
    }

    @Override
    public Double getMaxPoints() {
        return getRawMaxPoints();
    }

    @Override
    public String getQuestion() {
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
    @Lob
    public List<String> getCorrectAnswers() {
        return List.of();
    }

    @Override
    public double getResult() {
        return graded ? score : 0.0;
    }

    public void grade(double score, String feedback) {
        this.score = Math.max(0, Math.min(score, 100));
        this.feedback = feedback;
        this.graded = true;
    }

    public boolean isGraded() {
        return graded;
    }

    public double getScore() {
        return score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setGraded(boolean graded) {
        this.graded = graded;
    }

    @Override
    public String toString() {
        return "GradedQuestion {" +
                "id=" + getId() +
                ", question='" + getQuestion() + '\'' +
                ", userAnswer='" + getUserAnswer() + '\'' +
                ", graded=" + graded +
                ", score=" + score +
                ", feedback='" + feedback + '\'' +
                ", category='" + getCategory() + '\'' +
                '}';
    }
}
