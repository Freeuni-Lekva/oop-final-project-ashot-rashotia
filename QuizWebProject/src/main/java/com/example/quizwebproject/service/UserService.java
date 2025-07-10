package com.example.quizwebproject.service;

import com.example.quizwebproject.model.users.User;
import com.example.quizwebproject.model.users.chat.Chat;
import com.example.quizwebproject.repos.ChatRepo;
import com.example.quizwebproject.repos.UserRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public boolean isValidPass(@NotNull User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        User DBUser = userRepo.findByUsername(username);
        if(DBUser != null) {
            return DBUser.getPassword().equals(password);
        }
        return false;
    }

    public boolean userExists(@NotNull User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        User DBUser = userRepo.findByUsername(username);
        return DBUser != null;
    }

    public void addNewUser(@NotNull User user) {
        userRepo.save(user);
    }

    public User getUser(@NotNull String username) {
        return userRepo.findByUsername(username);
    }

    public List<Chat> getUserChats(@NotNull Long userId) {
        User user = userRepo.findById(userId).orElse(null);
        if(user == null) throw new RuntimeException("User not found");
        return user.getChats();
    }

    public List<User> getUserFriends(@NotNull Long userId) {
        User user = userRepo.findById(userId).orElse(null);
        if(user == null) throw new RuntimeException("User not found");
        return user.getFriends();
    }

    public User getUserByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
