package com.example.quizwebproject.DTOs;

import java.time.LocalDateTime;

public class AnnouncementDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime dateTime;
    private String authorUsername;

    public AnnouncementDTO() {
    }

    public AnnouncementDTO(Long id, String title, String content, LocalDateTime dateTime, String authorUsername) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.authorUsername = authorUsername;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}
