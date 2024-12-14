package com.adev.vedacommunity.comment.repository;

import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.article.repository.ActiveArticleRepository;
import com.adev.vedacommunity.article.repository.ArticleRepository;
import com.adev.vedacommunity.comment.entity.Comment;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import com.adev.vedacommunity.user.repository.CommunityUserViewRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest  {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommunityUserRepository communityUserRepository;
    @Autowired
    private CommunityUserViewRepository communityUserViewRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ActiveArticleRepository activeArticleRepository;

    @Autowired
    private EntityManager em;
    @Autowired
    private ActiveCommentRepository activeCommentRepository;

    @Test
    public void 댓글_뷰_테스트(){

        CommunityUser user = new CommunityUser("test@gmail.com", "TestNickname");
        CommunityUser saved = communityUserRepository.save(user);
        CommunityUserView view = communityUserViewRepository.findById(saved.getId()).orElseThrow(() -> new RuntimeException("test"));

        Article article = new Article("testAritcle","testContent",view);
        Article savedArticle = articleRepository.save(article);
        ActiveArticle active = activeArticleRepository.findById(savedArticle.getId()).orElseThrow(() -> new RuntimeException("No article"));

        Comment comment = new Comment("testCommentContent",view, active);
        commentRepository.save(comment);
        em.flush();
        em.clear();

        Assertions.assertThat(commentRepository.findAll().size()).isEqualTo(activeCommentRepository.findAll().size());

    }

}