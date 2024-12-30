package com.adev.vedacommunity.comment.repository;

import com.adev.vedacommunity.anonarticle.entity.ActiveAnonArticle;
import com.adev.vedacommunity.anonarticle.entity.AnonArticle;
import com.adev.vedacommunity.anonarticle.repository.ActiveAnonArticleRepository;
import com.adev.vedacommunity.anonarticle.repository.AnonArticleRepository;
import com.adev.vedacommunity.anoncomment.entity.AnonComment;
import com.adev.vedacommunity.anoncomment.repository.AnonCommentRepository;
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
class AnonCommentRepositoryTest {

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
    @Autowired
    private AnonArticleRepository anonArticleRepository;
    @Autowired
    private AnonCommentRepository anonCommentRepository;
    @Autowired
    private ActiveAnonArticleRepository activeAnonArticleRepository;

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

    @Test
    public void findByArticleID_테스트(){

        CommunityUser user = new CommunityUser("test@gmail.com", "TestNickname");
        CommunityUser savedCommunityUser = communityUserRepository.save(user);
        CommunityUserView view = communityUserViewRepository.findById(savedCommunityUser.getId()).get();


        AnonArticle anonArticle = new AnonArticle("익명 게시판", "익명 게시판 내용",view);
        AnonArticle savedAnonArticle = anonArticleRepository.save(anonArticle);
        ActiveAnonArticle activeAnonArticle = activeAnonArticleRepository.findById(savedAnonArticle.getId()).get();

        for(int i =0; i<4; i++){
            anonCommentRepository.save(new AnonComment("testComment"+ i,view,activeAnonArticle));
        }

        Assertions.assertThat(anonCommentRepository.findByAnonArticleId(savedAnonArticle.getId()).size()).isEqualTo(4);


    }


}