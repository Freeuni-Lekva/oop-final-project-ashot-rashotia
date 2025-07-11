package com.example.quizwebproject.model.questions;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class MultiSelectQuestion extends Question {

    @Lob
    private String options;

    public MultiSelectQuestion() {}


    public MultiSelectQuestion(String question, List<String> options, List<String> correctAnswers, String category, Double maxPoints) {
        super(question, String.join(",", correctAnswers), category, maxPoints);
        this.options = String.join(",", options);
        setRawUserAnswer("");
    }

    public List<String> getOptions() {
        if (options == null || options.isBlank()) return new ArrayList<>();
        return Arrays.stream(options.split(","))
                .map(String::trim)
                .toList();
    }

    public void setOptions(List<String> options) {
        this.options = String.join(",", options);
    }

    @Override
    public String getQuestionType() { return "MultiSelectQuestion"; }

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
    public List<String> getCorrectAnswers() {
        String correctRaw = getRawCorrectAnswers();
        if (correctRaw == null || correctRaw.isBlank()) return new ArrayList<>();
        return Arrays.stream(correctRaw.split(","))
                .map(String::trim)
                .toList();
    }

    public void setCorrectAnswers(List<String> correctAnswers) {
        setRawCorrectAnswers(String.join(",", correctAnswers));
    }

    public List<String> getUserAnswers() {
        String userRaw = getRawUserAnswer();
        if (userRaw == null || userRaw.isBlank()) return new ArrayList<>();
        return Arrays.stream(userRaw.split(","))
                .map(String::trim)
                .toList();
    }

    @Override
    public double getResult() {
        List<String> correct = getCorrectAnswers();
        List<String> user = getUserAnswers();

        if (user.isEmpty()) return 0.0;

        int matched = 0;
        for (String ans : user) {
            if (correct.stream().anyMatch(c -> c.equalsIgnoreCase(ans))) {
                matched++;
            }
        }

        matched = Math.min(matched, correct.size());
        return 100.0 * matched / correct.size();
    }

    @Override
    public String toString() {
        return "MultiSelectQuestion {" +
                "id=" + getId() +
                ", question='" + getQuestion() + '\'' +
                ", options=" + getOptions() +
                ", correctAnswers=" + getCorrectAnswers() +
                ", userAnswers=" + getUserAnswers() +
                ", result=" + getResult() +
                ", category='" + getCategory() + '\'' +
                ", maxPoints=" + getMaxPoints() +
                '}';
    }
}
