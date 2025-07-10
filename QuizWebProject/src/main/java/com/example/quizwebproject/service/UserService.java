package com.example.quizwebproject.service;

import com.example.quizwebproject.model.users.User;
import com.example.quizwebproject.model.users.chat.Chat;
import com.example.quizwebproject.repos.ChatRepo;
import com.example.quizwebproject.repos.UserRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final ChatRepo chatRepo;

    private final UserRepo userRepo;

    public UserService(ChatRepo chatRepo, UserRepo userRepo) {
        this.chatRepo = chatRepo;
        this.userRepo = userRepo;
    }


    // me davamate eseni chatshi mchirdeboda

    public void addUserToChat(@NotNull Long chatId, @NotNull Long userId) {
        User user = userRepo.findById(userId).orElse(null);
        if(user == null) throw new RuntimeException("User not found");
        Chat chat = chatRepo.findById(chatId).orElse(null);
        if(chat == null) throw new RuntimeException("Chat not found");
        user.getChats().add(chat);
    }

    public User getUserById(@NotNull Long id) {
        return userRepo.findById(id).orElse(null);
    }
}
