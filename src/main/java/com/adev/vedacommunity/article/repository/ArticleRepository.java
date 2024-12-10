package com.adev.vedacommunity.article.repository;

import com.adev.vedacommunity.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a JOIN FETCH a.author auth LEFT JOIN FETCH auth.company WHERE a.id = :articleId")
    Optional<Article> findArticleWithAuthorById(@Param("articleId") Long articleId);

    @Query("SELECT a FROM Article a JOIN FETCH a.author auth LEFT JOIN FETCH auth.company")
    Page<Article> getArticleList(Pageable pageable);

}
