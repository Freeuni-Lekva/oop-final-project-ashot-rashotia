package com.example.quizwebproject.repos;

import com.example.quizwebproject.model.users.achievements.Achievements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementsRepo extends JpaRepository<Achievements, Long> {
}
