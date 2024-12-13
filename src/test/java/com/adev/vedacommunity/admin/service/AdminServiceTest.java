package com.adev.vedacommunity.admin.service;

import com.adev.vedacommunity.admin.entity.AdminCommunityUser;
import com.adev.vedacommunity.admin.repository.AdminCommunityRepository;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class AdminServiceTest {

    @Autowired
    private CommunityUserRepository communityUserRepository;

    @Autowired
    private AdminCommunityRepository adminCommunityRepository;

    @Test
    public void 올바르게_사용자가_전환된다(){
        AdminService adminService = new AdminService(adminCommunityRepository,communityUserRepository);

        CommunityUser communityUser = new CommunityUser("testUser", "testNickname");
        CommunityUser saved = communityUserRepository.save(communityUser);
        adminService.changeUsertoAdmin(saved.getId(),"Lee");
        CommunityUser user = communityUserRepository.findByEmail(communityUser.getEmail()).orElseThrow(() -> new RuntimeException("no"));
        assertThat(user.getEmail()).isEqualTo(communityUser.getEmail());
        assertThat(user).isInstanceOf(AdminCommunityUser.class);
        AdminCommunityUser adminUser = (AdminCommunityUser) user;
        assertThat((adminUser.getAdminUserName())).isEqualTo("Lee");

    }
}