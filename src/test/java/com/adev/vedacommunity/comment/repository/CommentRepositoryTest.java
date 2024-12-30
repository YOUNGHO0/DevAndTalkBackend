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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
public class CommentRepositoryTest {

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

    @Test
    @DisplayName("댓글이 게시글 ID로 올바르게 가져온다")
    public void findByArticleIdTest(){
        CommunityUser communityUser = new CommunityUser("test@gmail.com", "testuser");
        CommunityUser savedUser = communityUserRepository.save(communityUser);
        CommunityUserView communityUserView = communityUserViewRepository.findById(savedUser.getId()).orElseThrow(() -> new RuntimeException("no"));

        Article article = new Article("testArticle", "testContent", communityUserView);
        Article savedArticle = articleRepository.save(article);
        ActiveArticle activeArticle = activeArticleRepository.findById(savedArticle.getId()).orElseThrow(() -> new RuntimeException("no"));

        for(int i =0; i<4; i++){
            commentRepository.save(new Comment("commentcontent" + i,communityUserView,activeArticle));
        }

        Assertions.assertThat(commentRepository.findByArticleId(activeArticle.getId()).size()).isEqualTo(4);


    }




}
