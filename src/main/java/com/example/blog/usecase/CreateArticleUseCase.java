package com.example.blog.usecase;

import com.example.blog.domain.Article;
import com.example.blog.domain.ArticleRepository;
import com.example.blog.usecase.dto.ArticleResponse;
import com.example.blog.usecase.dto.CreateArticleCommand;

import java.util.UUID;

public class CreateArticleUseCase {

    private final ArticleRepository repo;

    public CreateArticleUseCase(ArticleRepository repo) {
        this.repo = repo;
    }

    public ArticleResponse execute(CreateArticleCommand cmd) {

        Article article = new Article(
                UUID.randomUUID(),
                cmd.title(),
                cmd.body()
        );

        Article saved = repo.save(article);

        return new ArticleResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getBody(),
                saved.getCreatedAt()
        );
    }
}
