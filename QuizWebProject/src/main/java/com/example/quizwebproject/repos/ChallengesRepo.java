package com.example.quizwebproject.repos;

import com.example.quizwebproject.model.users.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ChallengesRepo extends JpaRepository<Challenge, Long> {

    List<Challenge> findByReceiverId(Long userId);
}
