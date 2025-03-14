package com.adev.vedacommunity.article.service;

import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.article.repository.ActiveArticleRepository;
import com.adev.vedacommunity.article.repository.ArticleRepository;
import com.adev.vedacommunity.comment.service.CommentService;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ActiveArticleRepository activeArticleRepository;
    private final CommentService commentService;

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


    public void update(long id,String title, String content, CommunityUserView user) {
        articleRepository.findById(id).ifPresent((article)-> {
        if(article.canUpdate(title, content, user)){
            article.update(title,content,user);
        }
     });
    }

    public void deleteBy(long id, CommunityUserView user){
        articleRepository.findById(id).ifPresent(article -> {
            if(article.canDelete(user)){
                article.delete();
                commentService.deleteAllByArticleId(id);
            }
        });


    }

    public void deleteAll(CommunityUserView user){
        articleRepository.findByAuthor(user).forEach(article -> {
            if(article.canDelete(user)) {
                article.delete();
            }
        });
    }
}
