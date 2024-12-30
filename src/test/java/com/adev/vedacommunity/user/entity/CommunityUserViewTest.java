package com.adev.vedacommunity.user.entity;

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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@ActiveProfiles("local")
class CommunityUserViewTest {


    @Autowired
    private CommunityUserRepository communityUserRepository;
    @Autowired
    private CommunityUserViewRepository communityUserViewRepository;

    @Test
    @DisplayName("equals 테스트")
    public void equalsTest() {

        CommunityUser communityUser = new CommunityUser("test@gmail.com", "testuser");
        CommunityUser savedUser = communityUserRepository.save(communityUser);
        CommunityUserView communityUserView = communityUserViewRepository.findById(savedUser.getId()).orElseThrow(() -> new RuntimeException("no"));

        CommunityUser notAuthor = new CommunityUser("test2@gmail.com", "testuser2");
        CommunityUser savedNotAuthor = communityUserRepository.save(notAuthor);
        CommunityUserView savedNotAuthorView = communityUserViewRepository.findById(savedNotAuthor.getId()).orElseThrow(() -> new RuntimeException("no"));


        Assertions.assertThat(communityUserView).isNotEqualTo(savedNotAuthorView);

    }

}