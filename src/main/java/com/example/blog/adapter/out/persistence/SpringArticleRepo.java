package com.example.blog.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface SpringArticleRepo extends MongoRepository<ArticleEntity, UUID> {
}
