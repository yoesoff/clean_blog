package com.example.blog.adapter.in.web;

import com.example.blog.usecase.CreateArticleUseCase;
import com.example.blog.usecase.dto.ArticleResponse;
import com.example.blog.usecase.dto.CreateArticleCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final CreateArticleUseCase createArticleUseCase;

    public ArticleController(CreateArticleUseCase useCase) {
        this.createArticleUseCase = useCase;
    }

    @PostMapping
    public ArticleResponse create(@RequestBody CreateArticleCommand cmd) {
        return createArticleUseCase.execute(cmd);
    }
}
