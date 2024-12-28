package com.adev.vedacommunity.anonarticle.repository;

import com.adev.vedacommunity.anonarticle.entity.AnonArticle;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ActiveAnonArticleRepositoryTest {

    @Autowired
    private ActiveAnonArticleRepository activeAnonArticleRepository;

    @Autowired
    private AnonArticleRepository anonArticleRepository;
    @Autowired
    private CommunityUserRepository communityUserRepository;
    @Autowired
    private CommunityUserViewRepository communityUserViewRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("올바르게 뷰가 게시판 목록을 보여준다")
    public void test(){

        CommunityUser user = new CommunityUser("test@gmail.com", "testNickName");

        CommunityUser saved = communityUserRepository.save(user);

        em.flush();
        em.clear();

        long originalAnonArticleSize = anonArticleRepository.count();
        long originalActiveAnonArticleSize = activeAnonArticleRepository.count();

        System.out.println(communityUserViewRepository.findAll().size());
        CommunityUserView author = communityUserViewRepository.findById(saved.getId()).orElseThrow(() -> new RuntimeException("no"));


        for(int i =0; i<20; i++){
            AnonArticle anonArticle = new AnonArticle("title" + i ,"test title" + i,author);
            anonArticleRepository.save(anonArticle);
        }

        for(int i =0; i<4; i++){
            AnonArticle anonArticle = new AnonArticle("title" + i ,"test title" + i,author);
            anonArticle.delete();
            anonArticleRepository.save(anonArticle);
        }


        Assertions.assertThat(anonArticleRepository.count()).isEqualTo(originalAnonArticleSize+24);
        Assertions.assertThat(activeAnonArticleRepository.count()).isEqualTo(originalActiveAnonArticleSize+20);



    }

}