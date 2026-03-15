package com.example.blog.usecase.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ArticleResponse(
        UUID id,
        String title,
        String body,
        LocalDateTime createdAt
) {}
