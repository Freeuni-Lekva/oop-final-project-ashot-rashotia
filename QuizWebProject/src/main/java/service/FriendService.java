package service;

import com.example.quizwebproject.repos.FriendRequestRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FriendService {

    private final FriendRequestRepo friendRequestRepo;

    public FriendService(FriendRequestRepo friendRequestRepo) {
        this.friendRequestRepo = friendRequestRepo;
    }
}
