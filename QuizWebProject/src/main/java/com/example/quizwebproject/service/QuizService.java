package com.example.quizwebproject.service;

import com.example.quizwebproject.DTOs.statsDTO;
import com.example.quizwebproject.model.questions.Question;
import com.example.quizwebproject.model.quizes.Quiz;
import com.example.quizwebproject.model.quizes.QuizResult;
import com.example.quizwebproject.model.users.User;
import com.example.quizwebproject.model.users.activities.FriendActivity;
import com.example.quizwebproject.model.users.activities.QuizTaken;
import com.example.quizwebproject.repos.FriendActivityRepo;
import com.example.quizwebproject.repos.QuizRepo;
import com.example.quizwebproject.repos.QuizResultRepo;
import com.example.quizwebproject.repos.UserRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class QuizService {
    private final QuizRepo quizRepo;

    private final UserRepo userRepo;

    private final FriendActivityRepo fracRepo;

    private final QuizResultRepo quizResultRepo;

    public QuizService(QuizRepo quizRepo, UserRepo userRepo, FriendActivityRepo fracRepo, QuizResultRepo quizResultRepo) {
        this.userRepo = userRepo;
        this.quizRepo = quizRepo;
        this.fracRepo = fracRepo;
        this.quizResultRepo = quizResultRepo;
    }

    public Quiz getQuizById(Long id) {
        return quizRepo.findById(id).orElse(null);
    }

    public double calculateScore(@NotNull Quiz quiz) {
        List<Question> questions = quiz.getQuestions();
        double score = 0;
        for(Question q: questions) {
            score += q.getResult();
        }
        return score;
    }

    public void addNewResult(long elapsedTime, double score, Long quizId, Long userId) {
        Quiz quiz = quizRepo.findById(quizId).orElse(null);
        User user = userRepo.findById(userId).orElse(null);
        if(quiz == null || user == null) throw new IllegalArgumentException("Quiz or User not found");

        QuizResult result = new QuizResult(elapsedTime, score, quiz, user);
        quiz.getHistory().add(result);
        user.getUserHistory().add(result);

        FriendActivity act = new QuizTaken(user.getId(),result.getId(),user.getUsername());
        User us = userRepo.findById(quiz.getAuthor().getId()).orElseThrow(()->new RuntimeException("User not found"));

        fracRepo.save(act);
    }

    public List<Quiz> getAllQuizzs() {
        return quizRepo.findAll();
    }

    public List<QuizResult> getUserPastPerformance(Long userId, Long quizId) {
        return quizResultRepo.pastPerformance(userId,quizId);
    }

    public List<QuizResult> getAllTimeLeaderboard(Long quizId) {
        return quizResultRepo.getQuizLeaderboard(quizId);
    }

    public List<QuizResult> getLastDayLb(Long quizId) {
        LocalDateTime lastDay = LocalDateTime.now().minusDays(1);
        return quizResultRepo.getLastDayLb(quizId,lastDay);
    }

    public List<QuizResult> getRecentHistory(Long quizId) {
        LocalDateTime lastDay = LocalDateTime.now().minusDays(1);
        return quizResultRepo.getRecentHistory(quizId,lastDay);
    }

    public statsDTO getStats(Long quizId) {
        Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        List<QuizResult> hist = quizResultRepo.getByQuizId(quizId);
        if (hist.isEmpty()) {
            return new statsDTO(0.0, 0.0, 0.0, 0L, 0);
        }
        long totalTime = 0L;
        double totalScore = 0D;
        double maxScore = Double.MIN_VALUE;
        double minScore = Double.MAX_VALUE;
        HashSet<User> users = new HashSet<>();

        for (QuizResult qr : hist) {
            users.add(qr.getUser());
            totalTime += qr.getTime();
            totalScore += qr.getPoints();
            maxScore = Math.max(maxScore, qr.getPoints());
            minScore = Math.min(minScore, qr.getPoints());
        }
        int userCount = users.size();
        double avgScore = totalScore / userCount;
        long avgTime = totalTime / userCount;
        return new statsDTO(avgScore, maxScore, minScore, avgTime, userCount);
    }

    public String trimBrackets(String input) {
        if (input != null && input.startsWith("[") && input.endsWith("]") && input.length() > 1) {
            return input.substring(1, input.length() - 1).trim();
        }
        return input;
    }
}
