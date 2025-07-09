package com.example.quizwebproject.repos;

import com.example.quizwebproject.model.users.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepo extends JpaRepository<Message, Long> {
}
