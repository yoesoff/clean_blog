package com.example.blog.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("articles")
public class ArticleEntity {

    @Id
    private UUID id;

    private String title;
    private String body;

    private LocalDateTime createdAt;
}
