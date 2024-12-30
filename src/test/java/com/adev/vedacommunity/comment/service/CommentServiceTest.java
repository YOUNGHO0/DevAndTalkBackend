package com.adev.vedacommunity.comment.service;

import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.article.repository.ActiveArticleRepository;
import com.adev.vedacommunity.article.repository.ArticleRepository;
import com.adev.vedacommunity.article.service.ArticleService;
import com.adev.vedacommunity.comment.entity.Comment;
import com.adev.vedacommunity.comment.repository.ActiveCommentRepository;
import com.adev.vedacommunity.comment.repository.CommentRepository;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import com.adev.vedacommunity.user.repository.CommunityUserViewRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("local")
class CommentServiceTest {


    @Autowired
    ArticleService articleService;
    @Autowired
    private CommunityUserRepository communityUserRepository;
    @Autowired
    private CommunityUserViewRepository communityUserViewRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ActiveArticleRepository activeArticleRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ActiveCommentRepository activeCommentRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("올바른 요청은 해당 게시글의 댓글들이 삭제처리 된다.")
    public void deleteAllByArticleIdTest(){


        CommunityUser communityUser = new CommunityUser("test@gmail.com", "testuser");
        CommunityUser savedUser = communityUserRepository.save(communityUser);
        CommunityUserView communityUserView = communityUserViewRepository.findById(savedUser.getId()).orElseThrow(() -> new RuntimeException("no"));

        Article article = new Article("testArticle", "testContent", communityUserView);
        Article savedArticle = articleRepository.save(article);
        ActiveArticle activeArticle = activeArticleRepository.findById(savedArticle.getId()).orElseThrow(() -> new RuntimeException("no"));

        for(int i =0; i<4; i++){
            commentRepository.save(new Comment("commentcontent" + i,communityUserView,activeArticle));
        }

        articleService.deleteBy(savedArticle.getId(),communityUserView);
        em.flush();
        em.clear();
        assertThat(activeCommentRepository.findAllByArticleId(savedArticle.getId()).size()).isEqualTo(0);

        List<Comment> comments = commentRepository.findByArticleId(savedArticle.getId());

        assertThat(comments)
                .isNotEmpty() // 리스트가 비어 있지 않은지 확인 (필요에 따라 제거 가능)
                .allSatisfy(comment -> assertThat(comment.isDeleted()).isTrue());


    }

    @Test
    @DisplayName("글 작성자만 게시글 삭제가 가능하다")
    public void onlyAuthorCanDeleteArticle(){


        CommunityUser communityUser = new CommunityUser("test@gmail.com", "testuser");
        CommunityUser savedUser = communityUserRepository.save(communityUser);
        CommunityUserView communityUserView = communityUserViewRepository.findById(savedUser.getId()).orElseThrow(() -> new RuntimeException("no"));

        CommunityUser notAuthor = new CommunityUser("test2@gmail.com", "testuser2");
        CommunityUser savedNotAuthor = communityUserRepository.save(notAuthor);
        CommunityUserView savedNotAuthorView = communityUserViewRepository.findById(savedNotAuthor.getId()).orElseThrow(() -> new RuntimeException("no"));

        Article article = new Article("testArticle", "testContent", communityUserView);
        Article savedArticle = articleRepository.save(article);
        ActiveArticle activeArticle = activeArticleRepository.findById(savedArticle.getId()).orElseThrow(() -> new RuntimeException("no"));

        for(int i =0; i<4; i++){
            commentRepository.save(new Comment("commentcontent" + i,communityUserView,activeArticle));
        }
        em.flush();
        em.clear();

        assertThatThrownBy(() -> articleService.deleteBy(savedArticle.getId(), savedNotAuthorView))
                .isInstanceOf(RuntimeException.class);

    }






}