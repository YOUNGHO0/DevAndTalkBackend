package com.adev.vedacommunity.article.repository;

import com.adev.vedacommunity.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ArticleRepository extends JpaRepository<Article, Long> {


}
