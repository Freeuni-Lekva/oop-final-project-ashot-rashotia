package com.example.quizwebproject.model.questions;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class MatchingQuestion extends Question {

    @Lob
    private String leftItems;

    @Lob
    private String rightItems;

    @Lob
    private String correctMatches;

    private int maxPoints;

    public MatchingQuestion() {}

    public MatchingQuestion(
            String question,
            List<String> leftItems,
            List<String> rightItems,
            List<String> correctMatches,
            String category,
            int maxPoints) {

        super(question, String.join(",", correctMatches), category); // store correctMatches in base
        this.leftItems = String.join(",", leftItems);
        this.rightItems = String.join(",", rightItems);
        this.correctMatches = String.join(",", correctMatches);
        this.maxPoints = maxPoints;
        setRawUserAnswer("");
    }

    public List<String> getLeftItems() {
        return parseList(leftItems);
    }

    public void setLeftItems(List<String> leftItems) {
        this.leftItems = String.join(",", leftItems);
    }

    public List<String> getRightItems() {
        return parseList(rightItems);
    }

    public void setRightItems(List<String> rightItems) {
        this.rightItems = String.join(",", rightItems);
    }

    @Override
    public List<String> getCorrectAnswers() {
        return parseList(correctMatches);
    }

    public void setCorrectAnswers(List<String> correctMatches) {
        this.correctMatches = String.join(",", correctMatches);
        setRawCorrectAnswers(this.correctMatches);
    }

    public List<String> getUserMatches() {
        return parseList(getRawUserAnswer());
    }

    public void setUserMatches(List<String> matches) {
        setRawUserAnswer(String.join(",", matches));
    }

    @Override
    public double getResult() {
        List<String> correct = getCorrectAnswers();
        List<String> user = getUserMatches();

        if (user.isEmpty() || user.size() != correct.size()) return 0.0;

        int matched = 0;
        for (int i = 0; i < correct.size(); i++) {
            if (correct.get(i).equalsIgnoreCase(user.get(i))) {
                matched++;
            }
        }

        return 100.0 * matched / correct.size();
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
    public String toString() {
        return "MatchingQuestion {" +
                "id=" + getId() +
                ", question='" + getQuestion() + '\'' +
                ", leftItems=" + getLeftItems() +
                ", rightItems=" + getRightItems() +
                ", correctMatches=" + getCorrectAnswers() +
                ", userMatches=" + getUserMatches() +
                ", result=" + getResult() +
                ", category='" + getCategory() + '\'' +
                ", maxPoints=" + maxPoints +
                '}';
    }

    private List<String> parseList(String str) {
        if (str == null || str.isBlank()) return new ArrayList<>();
        return Arrays.stream(str.split(","))
                .map(String::trim)
                .toList();
    }
}
