package com.adev.vedacommunity.article.entity;

import com.adev.vedacommunity.article.repository.ActiveArticleRepository;
import com.adev.vedacommunity.article.repository.ArticleRepository;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import com.adev.vedacommunity.user.repository.CommunityUserViewRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
class ActiveArticleTest {


    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ActiveArticleRepository activeArticleRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CommunityUserRepository communityUserRepository;
    @Autowired
    private CommunityUserViewRepository communityUserViewRepository;
    @Test
    @DisplayName("뷰에서 올바른 값을 반환한다")
    public void testCase1(){
        CommunityUser user = new CommunityUser("testUser", "testNickname");
        CommunityUser saved = communityUserRepository.save(user);
        entityManager.flush();
        entityManager.clear();
        CommunityUserView byId = communityUserViewRepository.findById(saved.getId()).get();
        for(int i =0; i<20; i++){
            Article article = new Article("title"+i,"content"+i,byId);
            articleRepository.save(article);
        }

        System.out.println(activeArticleRepository.findAll().size());
    }



}