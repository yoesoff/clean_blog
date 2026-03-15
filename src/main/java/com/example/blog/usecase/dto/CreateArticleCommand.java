package com.example.blog.usecase.dto;

public record CreateArticleCommand(
        String title,
        String body
) {}
