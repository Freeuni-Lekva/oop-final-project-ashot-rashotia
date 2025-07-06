package com.example.quizwebproject.model.questions;

import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class MultiAnswerQuestion extends Question {

    private boolean ordered;
    private int maxPoints;

    public MultiAnswerQuestion() {}

    public MultiAnswerQuestion(String question, List<String> correctAnswers, String category, boolean ordered, int maxPoints) {
        super(question, String.join(",", correctAnswers), category);
        this.ordered = ordered;
        this.maxPoints = maxPoints;
        setRawUserAnswer("");
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    @Override
    public String getQuestion() {
        return getRawQuestion();
    }

    public void setQuestion(String question) {
        setRawQuestion(question);
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
        String raw = getRawCorrectAnswers();
        if (raw == null || raw.isEmpty()) return new ArrayList<>();
        return Arrays.stream(raw.split(","))
                .map(String::trim)
                .toList();
    }

    public void setCorrectAnswers(List<String> correctAnswers) {
        setRawCorrectAnswers(String.join(",", correctAnswers));
    }

    public List<String> getUserAnswers() {
        String raw = getRawUserAnswer();
        if (raw == null || raw.isEmpty()) return new ArrayList<>();
        return Arrays.stream(raw.split(","))
                .map(String::trim)
                .toList();
    }

    @Override
    public double getResult() {
        List<String> correct = getCorrectAnswers();
        List<String> user = getUserAnswers();

        if (user.isEmpty()) return 0.0;

        int matched = 0;

        if (ordered) {
            int minLen = Math.min(correct.size(), user.size());
            for (int i = 0; i < minLen; i++) {
                if (correct.get(i).equalsIgnoreCase(user.get(i))) {
                    matched++;
                }
            }
        } else {
            for (String ans : user) {
                if (correct.stream().anyMatch(c -> c.equalsIgnoreCase(ans))) {
                    matched++;
                }
            }
            matched = Math.min(matched, correct.size());
        }

        return 100.0 * matched / correct.size();
    }

    @Override
    public String toString() {
        return "MultiAnswerQuestion {" +
                "id=" + getId() +
                ", question='" + getQuestion() + '\'' +
                ", correctAnswers=" + getCorrectAnswers() +
                ", userAnswers=" + getUserAnswers() +
                ", result=" + getResult() +
                ", category='" + getCategory() + '\'' +
                ", ordered=" + ordered +
                ", maxPoints=" + maxPoints +
                '}';
    }
}
