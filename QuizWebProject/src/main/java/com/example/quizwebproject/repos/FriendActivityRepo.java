package com.example.quizwebproject.repos;

import com.example.quizwebproject.model.users.activities.FriendActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendActivityRepo extends JpaRepository<FriendActivity, Long> {
    //
}
