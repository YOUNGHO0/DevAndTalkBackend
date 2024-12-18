package com.adev.vedacommunity.comment.entity;

import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.article.repository.ActiveArticleRepository;
import com.adev.vedacommunity.article.repository.ArticleRepository;
import com.adev.vedacommunity.comment.dto.request.ChildCommentCreateRequestDto;
import com.adev.vedacommunity.comment.dto.request.CommentCreateRequestDto;
import com.adev.vedacommunity.comment.mapper.CommentMapper;
import com.adev.vedacommunity.comment.repository.ActiveCommentRepository;
import com.adev.vedacommunity.comment.repository.CommentRepository;
import com.adev.vedacommunity.comment.service.CommentService;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import com.adev.vedacommunity.user.repository.CommunityUserViewRepository;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.AssertTrue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentTest {


    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ActiveCommentRepository activeCommentRepository;
    @Autowired
    private CommunityUserRepository communityUserRepository;
    @Autowired
    private CommunityUserViewRepository communityUserViewRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ActiveArticleRepository activeArticleRepository;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private EntityManager em;
    @Autowired
    private CommentService commentService;
    @Test
    public void 댓글_요청시_올바르게_댓글이_맵핑된다(){

        String commentContent = "testContent";
        CommentCreateRequestDto dto = new CommentCreateRequestDto(1,commentContent);
        CommunityUser user = new CommunityUser("test@gmail.com", "nickname");
        CommunityUser saved = communityUserRepository.save(user);

        CommunityUserView communityUser = communityUserViewRepository.findById(saved.getId()).orElseThrow(() -> new RuntimeException("no"));
        Article article = new Article("title","content", communityUser);
        articleRepository.save(article);
        ActiveArticle activeArticle = activeArticleRepository.findById(article.getId()).orElseThrow(() -> new RuntimeException("no"));

        Comment comment = commentMapper.toComment(dto.getCommentContent(),communityUser,activeArticle);

        Assertions.assertThat(comment.getArticle()).isEqualTo(activeArticle);
        Assertions.assertThat(comment.getCommentAuthor()).isEqualTo(communityUser);
        Assertions.assertThat(comment.getCommentContent()).isEqualTo(commentContent);
    }


    @Test
    @Transactional
//    @Rollback(false)
    public void 부모_댓글_삭제_테스트(){

        String commentContent = "testContent";

        CommunityUser user = new CommunityUser("test@gmail.com", "nickname");
        CommunityUser saved = communityUserRepository.save(user);
        CommunityUserView communityUser = communityUserViewRepository.findById(saved.getId()).orElseThrow(() -> new RuntimeException("no"));

        Article article = new Article("title","content", communityUser);
        articleRepository.save(article);
        ActiveArticle activeArticle = activeArticleRepository.findById(article.getId()).orElseThrow(() -> new RuntimeException("no"));

        CommentCreateRequestDto dto = new CommentCreateRequestDto(1,commentContent);
        Comment parentComment = commentMapper.toComment(dto.getCommentContent(),communityUser,activeArticle);
        Comment savedComment = commentRepository.save(parentComment);
        ChildCommentCreateRequestDto childDto = new ChildCommentCreateRequestDto(savedComment.getId(),"childComment");
        Comment comment = commentMapper.toChildComment(childDto,parentComment,communityUser,activeArticle);
        Comment savedChildComment = commentRepository.save(comment);

        em.flush();
        em.clear();

        commentService.deleteComment(parentComment.getId(),communityUser);

        em.flush();
        em.clear();
        Assertions.assertThat(activeCommentRepository.findAll().size()).isEqualTo(1);
        ActiveComment savedChildComment2 = activeCommentRepository.findById(savedChildComment.getId()).get();
        System.out.println(savedChildComment2.getParentCommentId());

        Assertions.assertThat(commentRepository.findAll().size()).isEqualTo(2);
        Assertions.assertThat(commentRepository.findById(savedComment.getId()).isEmpty()).isEqualTo(false);
        List<ActiveComment> all = activeCommentRepository.findAll();

        for(ActiveComment s : all){
            System.out.println(s.getCommentContent());
        }


    }

}