package com.adev.vedacommunity.article.service;

import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.article.repository.ActiveArticleRepository;
import com.adev.vedacommunity.article.repository.ArticleRepository;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ActiveArticleRepository activeArticleRepository;
    public void write(String title, String content, CommunityUserView user){

        Article article = new Article(title, content, user);
        articleRepository.save(article);

    }
    public void write(Article article){
        articleRepository.save(article);
    }
    public Optional<ActiveArticle> read(long id){
        return activeArticleRepository.findArticleWithAuthorById(id);
    }


    public void update(long id,String title, String content, CommunityUser user) {
        articleRepository.findById(id).ifPresent((article)->{ article.update(title, content, user);});
    }

    public void delete(long id, CommunityUser user){
        articleRepository.findById(id).ifPresent((article)->{ if(article.canDelete(user)) articleRepository.delete(article);});
    }
}
