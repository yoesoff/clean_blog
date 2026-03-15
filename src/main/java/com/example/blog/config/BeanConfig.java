package com.example.blog.config;

import com.example.blog.adapter.out.persistence.ArticleMapper;
import com.example.blog.adapter.out.persistence.ArticleRepoAdapter;
import com.example.blog.adapter.out.persistence.SpringArticleRepo;
import com.example.blog.domain.ArticleRepository;
import com.example.blog.usecase.CreateArticleUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ArticleMapper articleMapper() {
        return new ArticleMapper();
    }

    @Bean
    public ArticleRepository articleRepository(
            SpringArticleRepo repo,
            ArticleMapper mapper
    ) {
        return new ArticleRepoAdapter(repo, mapper);
    }

    @Bean
    public CreateArticleUseCase createArticleUseCase(
            ArticleRepository repo
    ) {
        return new CreateArticleUseCase(repo);
    }
}
