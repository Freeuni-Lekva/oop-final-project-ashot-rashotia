package com.example.quizwebproject.service;

import com.example.quizwebproject.DTOs.AnnouncementDTO;
import com.example.quizwebproject.model.users.Challenge;
import com.example.quizwebproject.model.users.activities.FriendActivity;
import com.example.quizwebproject.model.quizes.Quiz;
import com.example.quizwebproject.model.quizes.QuizResult;
import com.example.quizwebproject.model.users.User;
import com.example.quizwebproject.model.users.admin.Announcement;
import com.example.quizwebproject.repos.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomepageService {
    private final QuizRepo quizRepo;
    private final AnnouncementRepo announcementRepo;
    private final UserRepo userRepo;
    private final FriendActivityRepo fracRepo;

    public HomepageService(QuizRepo quizRepo, AnnouncementRepo announcementRepo, UserRepo userRepo,
                           FriendActivityRepo fracRepo) {
        this.quizRepo = quizRepo;
        this.announcementRepo = announcementRepo;
        this.userRepo = userRepo;
        this.fracRepo = fracRepo;
    }

    public List<AnnouncementDTO> getRecentAnnouncements(Pageable pageable) {
        LocalDateTime lastTenDays = LocalDateTime.now().minusDays(10);
        List<Announcement> announcements = this.announcementRepo.getRecentTenDayAnnouncements(lastTenDays, pageable).getContent();
        return announcements.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<AnnouncementDTO> getAllAnnouncements() {
        return this.announcementRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Quiz> popularQuizs() {
        return this.quizRepo.findByPopularity(PageRequest.of(0, 10)).getContent();
    }

    public List<Quiz> getRecentQuizs() {
        LocalDateTime lastDay = LocalDateTime.now().minusDays(1);
        return this.quizRepo.getDayLastTenQuizs(lastDay, PageRequest.of(0, 20)).getContent();
    }

    public List<QuizResult> getUserRecentQuizTakes(Long userId) {
        LocalDateTime lastTenDays = LocalDateTime.now().minusDays(10);
        return this.userRepo.getRecentUserQuizs(lastTenDays, userId, PageRequest.of(0, 20)).getContent();
    }

    public List<Quiz> getRecentQuizCreats(Long userId) {
        LocalDateTime lastTenDays = LocalDateTime.now().minusDays(10);
        return this.quizRepo.getRecentUserQuizCreations(userId, lastTenDays, PageRequest.of(0, 20)).getContent();
    }

    public List<Challenge> getRecentChallenges(Long userId) {
        return this.userRepo.getRecentChallenges(userId, PageRequest.of(0, 20));
    }

    public List<FriendActivity> getRecentFriendActivities(Long userId, Pageable pageable) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<User> friends = user.getFriends();
        List<FriendActivity> friendsRecentActs = new ArrayList<>();
        LocalDateTime lastDay = LocalDateTime.now().minusDays(1);

        for (User f : friends) {
            List<FriendActivity> recActs = this.fracRepo.findByUserId(f.getId(), lastDay, pageable).getContent();
            if (!recActs.isEmpty()) {
                friendsRecentActs.addAll(recActs);
            }
        }

        return friendsRecentActs;
    }

    private AnnouncementDTO toDto(Announcement announcement) {
        return new AnnouncementDTO(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getDateTime(),
                announcement.getAuthorName()
        );
    }
}
