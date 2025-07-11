package com.example.quizwebproject.controller;

import com.example.quizwebproject.model.users.User;
import com.example.quizwebproject.model.users.chat.Message;
import com.example.quizwebproject.service.ChatService;
import com.example.quizwebproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {
    private final ChatService chatService;

    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @GetMapping("/chat/{chatId}")
    public String openChat(@PathVariable Long chatId, Model model,
                           @RequestParam(value = "showAll", required = false) String showAll,
                           HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/login";

        List<Message> messages = List.of();
        if(showAll == null) {
            messages = chatService.getMessagesByChatId(chatId, 0, 100);
        }else if(showAll.equals("all")) {
            messages = chatService.seeAllMessagesByChatId(chatId);
        }
        model.addAttribute("chatId", chatId);
        model.addAttribute("messages", messages);
        model.addAttribute("user",user);
        return "chats/chat";
    }

    @GetMapping("/chatWithOneUser")
    public String createChatWithOneUser(HttpSession session,
                                        @RequestParam("clickedUserId") Long clickedUserId) {
        User first = (User) session.getAttribute("user");
        User second = userService.getUserById(clickedUserId);
        List<User> users = new ArrayList<>();
        users.add(first);
        users.add(second);
        Long chatId = chatService.startChat(users);
        return "redirect:/chat/" + chatId;
    }
}

