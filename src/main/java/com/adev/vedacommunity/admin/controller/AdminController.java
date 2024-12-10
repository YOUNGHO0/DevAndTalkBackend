package com.adev.vedacommunity.admin.controller;

import com.adev.vedacommunity.admin.dto.AdminUserListResponseDto;
import com.adev.vedacommunity.mapper.AdminMapper;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final CommunityUserRepository communityUserRepository;
    private final AdminMapper adminMapper;
    @GetMapping("")
    public ResponseEntity findAllUser(@AuthenticationPrincipal CommunityUser user, Pageable pageable) {

        Page<CommunityUser> all = communityUserRepository.findAll(pageable);
        Page<AdminUserListResponseDto> adminPageDto = adminMapper.toAdminPageReadDto(all);


        return ResponseEntity.ok().body(adminPageDto);
    }

}
