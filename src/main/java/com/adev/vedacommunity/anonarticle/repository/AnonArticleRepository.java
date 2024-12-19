package com.adev.vedacommunity.anonarticle.repository;

import com.adev.vedacommunity.anonarticle.entity.AnonArticle;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnonArticleRepository extends JpaRepository<AnonArticle, Long> {

    List<AnonArticle> findByAuthor(CommunityUserView author);
}
