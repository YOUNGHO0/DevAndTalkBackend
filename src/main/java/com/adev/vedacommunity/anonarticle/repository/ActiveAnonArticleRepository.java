package com.adev.vedacommunity.anonarticle.repository;

import com.adev.vedacommunity.anonarticle.entity.ActiveAnonArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActiveAnonArticleRepository extends JpaRepository<ActiveAnonArticle, Long> {

    Optional<ActiveAnonArticle> findById(Long articleId);
}
