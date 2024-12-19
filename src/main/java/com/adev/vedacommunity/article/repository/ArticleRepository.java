package com.adev.vedacommunity.article.repository;

import com.adev.vedacommunity.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import java.util.List;


public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByAuthor(CommunityUserView author);


}
