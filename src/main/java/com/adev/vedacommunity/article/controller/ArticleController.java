package com.adev.vedacommunity.article.controller;

import com.adev.vedacommunity.article.dto.request.ArticleCreateDto;
import com.adev.vedacommunity.article.dto.request.ArticleDeleteDto;
import com.adev.vedacommunity.article.dto.request.ArticleReadDto;
import com.adev.vedacommunity.article.dto.request.ArticleUpdateDto;
import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.article.mapper.ArticleMapper;
import com.adev.vedacommunity.article.repository.ActiveArticleRepository;
import com.adev.vedacommunity.article.repository.ArticleRepository;
import com.adev.vedacommunity.article.service.ArticleService;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ActiveArticleRepository articleRepository;
    @PostMapping("")
    public ResponseEntity createArticle (@Valid @RequestBody ArticleCreateDto dto, @AuthenticationPrincipal CommunityUserView communityUser) {

        Article article = articleMapper.toArticle(dto, communityUser);
        articleService.write(article);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getArticle(@PathVariable long id) {
        Optional<ActiveArticle> read = articleService.read(id); // id를 사용하여 데이터 조회
        if (read.isPresent()) {
            return ResponseEntity.ok(articleMapper.toArticleReadDto(read.get()));
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity getArticle(Pageable pageable){
        Page<ActiveArticle> list = articleRepository.getArticleList(pageable);
        return ResponseEntity.ok().body(articleMapper.toReadPageDto(list));
    }

    @PatchMapping("")
    public ResponseEntity updateArticle(@Valid @RequestBody ArticleUpdateDto dto, @AuthenticationPrincipal CommunityUserView communityUser){
        articleService.update(dto.getId(), dto.getTitle(),dto.getContent(), communityUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity deleteArticle(@Valid @RequestBody ArticleDeleteDto dto, @AuthenticationPrincipal CommunityUserView communityUser){
        articleService.deleteBy(dto.getDeleteId(), communityUser);
        return ResponseEntity.ok().build();
    }



}
