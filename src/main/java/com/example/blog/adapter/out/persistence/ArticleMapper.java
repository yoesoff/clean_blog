package com.example.blog.adapter.out.persistence;

import com.example.blog.domain.Article;

public class ArticleMapper {

    public ArticleEntity toEntity(Article article) {
        return new ArticleEntity(
                article.getId(),
                article.getTitle(),
                article.getBody(),
                article.getCreatedAt()
        );
    }

    public Article toDomain(ArticleEntity entity) {
        return new Article(
                entity.getId(),
                entity.getTitle(),
                entity.getBody()
        );
    }
}
