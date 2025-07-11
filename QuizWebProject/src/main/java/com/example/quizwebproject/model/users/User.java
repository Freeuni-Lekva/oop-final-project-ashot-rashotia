package com.example.quizwebproject.model.users;

import com.example.quizwebproject.model.quizes.Quiz;
import com.example.quizwebproject.model.quizes.QuizResult;
import com.example.quizwebproject.model.users.achievements.Achievements;
import com.example.quizwebproject.model.users.chat.Chat;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    private List<User> friends = new ArrayList<>();

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendRequest> sentRequests = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendRequest> pendingRequests = new ArrayList<>();

    // TODO : can prob change this
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<QuizResult> userHistory = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Challenge> challenges;

    @Column(nullable = false)
    private boolean admin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Achievements> achis;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = hashPassword(password);
        userHistory = new ArrayList<>();
        challenges = new ArrayList<>();
        achis = new ArrayList<>();
        admin = false;
    }

    public List<Achievements> getAchievements() { return achis; }

    public boolean isAdmin() { return admin; }

    public void promote() {
        this.admin = true;
    }

    public void demote() {
        this.admin = false;
    }

    public Long getId() { return id; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public List<User> getFriends() { return friends; }

    public List<Chat> getChats() { return chats; }

    public List<FriendRequest> getSentRequests() { return sentRequests; }

    @PreRemove
    public void preRemove() {
        List<User> frs = new ArrayList<>(friends);
        for (User friend : frs) {
            friend.getFriends().remove(this);
        }
        friends.clear();

        List<Chat> ch = new ArrayList<>(chats);
        for (Chat chat : ch) {
            chat.getUsers().remove(this);
        }
        chats.clear();

        List<QuizResult> qrs = this.userHistory;
        for (QuizResult qr : qrs) {
            List<QuizResult> quizQrs = qr.getQuiz().getHistory();
            quizQrs.remove(qr);
            qr.setQuiz(null);
            qr.setUser(null);
        }
    }

    public List<FriendRequest> getPendingRequests() { return pendingRequests; }

    public List<QuizResult> getUserHistory() { return userHistory; }

    // TODO : this and its usage in quiz class must move to service layer
    public void addResultToHistory(QuizResult result) {
        // This logic should ideally be placed in the service layer
        // Left here for now as per current architecture
        userHistory.add(result);
    }

    public void challengeAcceptedOrRejected(Challenge challenge) {
        challenges.remove(challenge);
    }

    public void receiveChallenge(Challenge challenge) {
        challenges.add(challenge);
    }

    public List<Challenge> getChallenges() { return challenges; }

    public QuizResult getBestScore(Quiz quiz) {
        return userHistory.stream()
                .filter(r -> r.getQuiz() != null && r.getQuiz().equals(quiz))
                .max(QuizResult.QUIZ_RESULT_COMPARATOR)
                .orElse(null);
    }


    private String hashPassword(String pas) {
        return Base64.getEncoder().encodeToString(pas.getBytes());
    }
}
