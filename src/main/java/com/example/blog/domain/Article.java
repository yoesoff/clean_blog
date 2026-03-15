package com.example.blog.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Article {

    private UUID id;
    private String title;
    private String body;
    private LocalDateTime createdAt;

    public Article(UUID id, String title, String body) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title required");
        }

        this.id = id;
        this.title = title;
        this.body = body;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
