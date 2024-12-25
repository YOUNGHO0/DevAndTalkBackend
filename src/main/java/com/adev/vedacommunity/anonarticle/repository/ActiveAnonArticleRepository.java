package com.adev.vedacommunity.anonarticle.repository;

import com.adev.vedacommunity.anonarticle.entity.ActiveAnonArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ActiveAnonArticleRepository extends JpaRepository<ActiveAnonArticle, Long> {

    Optional<ActiveAnonArticle> findById(Long articleId);

    @Query("SELECT a FROM ActiveAnonArticle a")
    Page<ActiveAnonArticle> getArticleList(Pageable pageable);
}
