package com.adev.vedacommunity.article.controller;

import com.adev.vedacommunity.article.dto.request.ArticleCreateDto;
import com.adev.vedacommunity.article.dto.request.ArticleDeleteDto;
import com.adev.vedacommunity.article.dto.request.ArticleReadDto;
import com.adev.vedacommunity.article.dto.request.ArticleUpdateDto;
import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.article.mapper.ArticleMapper;
import com.adev.vedacommunity.article.service.ArticleService;
import com.adev.vedacommunity.user.entity.CommunityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleMapper articleMapper;
    @PostMapping("")
    public ResponseEntity createArticle (ArticleCreateDto dto, @AuthenticationPrincipal CommunityUser communityUser) {

        Article article = articleMapper.toArticle(dto, communityUser);
        articleService.write(article);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity getArticle (@RequestBody ArticleReadDto readDto) {
        Optional<Article> read = articleService.read(readDto.getArticleId());
        if (read.isPresent()) {
            return ResponseEntity.ok(articleMapper.toArticleReadDto(read.get()));
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("")
    public ResponseEntity updateArticle(@RequestBody ArticleUpdateDto dto, @AuthenticationPrincipal CommunityUser communityUser ){
        articleService.update(dto.getId(), dto.getTitle(),dto.getContent(),communityUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity deleteArticle(@RequestBody ArticleDeleteDto dto, @AuthenticationPrincipal CommunityUser communityUser){
        articleService.delete(dto.getDeleteId(),communityUser);
        return ResponseEntity.ok().build();
    }



}
