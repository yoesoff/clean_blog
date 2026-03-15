package com.example.blog.adapter.out.persistence;

import com.example.blog.domain.Article;
import com.example.blog.domain.ArticleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ArticleRepoAdapter implements ArticleRepository {

    private final SpringArticleRepo springRepo;
    private final ArticleMapper mapper;

    public ArticleRepoAdapter(
            SpringArticleRepo springRepo,
            ArticleMapper mapper
    ) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public Article save(Article article) {
        ArticleEntity entity = mapper.toEntity(article);
        return mapper.toDomain(springRepo.save(entity));
    }

    @Override
    public Optional<Article> findById(UUID id) {
        return springRepo.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Article> findAll() {
        return springRepo.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        springRepo.deleteById(id);
    }
}
