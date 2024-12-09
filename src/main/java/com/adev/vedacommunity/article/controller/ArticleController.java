package com.adev.vedacommunity.article.controller;

import com.adev.vedacommunity.article.dto.request.ArticleCreateDto;
import com.adev.vedacommunity.article.dto.request.ArticleDeleteDto;
import com.adev.vedacommunity.article.dto.request.ArticleReadDto;
import com.adev.vedacommunity.article.dto.request.ArticleUpdateDto;
import com.adev.vedacommunity.article.service.ArticleService;
import com.adev.vedacommunity.user.entity.CommunityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("")
    public ResponseEntity createArticle (ArticleCreateDto dto, @AuthenticationPrincipal CommunityUser communityUser) {
        articleService.write(dto.getTitle(),dto.getContent(),communityUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity getArticle (ArticleReadDto readDto) {
        articleService.read(readDto.getArticleId());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("")
    public ResponseEntity updateArticle(ArticleUpdateDto dto, @AuthenticationPrincipal CommunityUser communityUser ){
        articleService.update(dto.getId(), dto.getTitle(),dto.getContent(),communityUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity deleteArticle(ArticleDeleteDto dto, CommunityUser communityUser){
        articleService.delete(dto.getDeleteId(),communityUser);
        return ResponseEntity.ok().build();
    }



}
