package com.example.blog.usecase.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateArticleCommand(
        @NotBlank(message = "Title tidak boleh kosong")
        @Size(min = 5, max = 100, message = "Title harus antara 5 hingga 100 karakter")
        String title,
        
        @NotBlank(message = "Body tidak boleh kosong")
        String body
) {}
