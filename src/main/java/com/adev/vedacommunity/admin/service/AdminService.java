package com.adev.vedacommunity.admin.service;

import com.adev.vedacommunity.admin.entity.AdminCommunityUser;
import com.adev.vedacommunity.admin.repository.AdminCommunityRepository;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminCommunityRepository adminCommunityRepository;
    private final CommunityUserRepository communityUserRepository;

    public void changeUsertoAdmin(long userId, String adminUserName){

        CommunityUser user = communityUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("no user"));
        communityUserRepository.delete(user);
        AdminCommunityUser adminUser = new AdminCommunityUser(user.getEmail(),user.getNickname(),adminUserName);
        adminCommunityRepository.save(adminUser);
    }
}
