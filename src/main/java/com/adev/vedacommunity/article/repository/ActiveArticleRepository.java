package com.adev.vedacommunity.article.repository;

import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ActiveArticleRepository extends JpaRepository< ActiveArticle, Long> {

    @Query("SELECT a FROM Article a JOIN FETCH a.author auth LEFT JOIN FETCH auth.company WHERE a.id = :articleId")
    Optional<ActiveArticle> findArticleWithAuthorById(@Param("articleId") Long articleId);

    @Query("SELECT a FROM Article a JOIN FETCH a.author auth LEFT JOIN FETCH auth.company")
    Page<ActiveArticle> getArticleList(Pageable pageable);
}
