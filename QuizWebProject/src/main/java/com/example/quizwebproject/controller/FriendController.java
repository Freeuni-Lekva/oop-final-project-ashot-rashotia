package com.example.quizwebproject.controller;

import com.example.quizwebproject.model.FriendRequest;
import com.example.quizwebproject.model.users.User;
import com.example.quizwebproject.service.FriendService;
import com.example.quizwebproject.service.HomepageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FriendController {
    private final FriendService friendService;
    private final HomepageService homepageService;

    public FriendController(FriendService friendService, HomepageService homepageService) {
        this.friendService = friendService;
        this.homepageService = homepageService;
    }

    @PostMapping("/friend/send")
    public String sendFriendRequest(@RequestParam("receiverId") Long receiverId, HttpSession session, RedirectAttributes redData) {
        User sen = (User)session.getAttribute("user");

        if (sen==null) {
            return "redirect:/login";
        }
        FriendRequest req = friendService.sendFriendRequest(sen.getId(), receiverId);
        if(req==null) {
            redData.addAttribute("newFriendRequestSent", false);
        } else {
            redData.addAttribute("newFriendRequestSent", true);
        }
        return "redirect:/user/"+receiverId;
    }

    @PostMapping("friend/accept")
    public String acceptFriendRequest(@RequestParam("requestId") Long reqId) {
        friendService.acceptFriendRequest(reqId);
        return "redirect:/homepage";
    }

    @PostMapping("friend/reject")
    public String rejectFriendRequest(@RequestParam("requestId") Long reqId) {
        friendService.rejectFriendRequest(reqId);
        return "redirect:/homepage";
    }
}
