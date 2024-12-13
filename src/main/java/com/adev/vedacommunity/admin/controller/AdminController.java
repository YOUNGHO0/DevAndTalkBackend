package com.adev.vedacommunity.admin.controller;

import com.adev.vedacommunity.admin.dto.AdminUserChangeRequestDto;
import com.adev.vedacommunity.admin.dto.AdminUserListResponseDto;
import com.adev.vedacommunity.admin.dto.AdminArticleDeleteRequestDto;
import com.adev.vedacommunity.admin.service.AdminService;
import com.adev.vedacommunity.article.repository.ArticleRepository;
import com.adev.vedacommunity.admin.mapper.AdminMapper;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.repository.CommunityUserViewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final CommunityUserViewRepository communityUserRepository;
    private final AdminMapper adminMapper;
    private final AdminService adminService;
    private final ArticleRepository articleRepository;
    @GetMapping("")
    public ResponseEntity findAllUser(@AuthenticationPrincipal CommunityUser user, Pageable pageable) {

        Page<CommunityUserView> all = communityUserRepository.findAll(pageable);
        Page<AdminUserListResponseDto> adminPageDto = adminMapper.toAdminPageReadDto(all);

        return ResponseEntity.ok().body(adminPageDto);
    }
    @PostMapping("/upgrade")
    public ResponseEntity changeUsertoAdmin(@AuthenticationPrincipal CommunityUserView user , @RequestBody AdminUserChangeRequestDto dto){

        adminService.changeUsertoAdmin(dto.getUserId(), dto.getAdminUserName());
        return ResponseEntity.ok().build();
    }

    @Transactional
    @DeleteMapping("/article")
    public ResponseEntity deleteArticleForce(@RequestBody AdminArticleDeleteRequestDto dto,  @AuthenticationPrincipal CommunityUserView user ){
        articleRepository.findById(dto.getArticleId()).ifPresent(article -> article.delete());
        return ResponseEntity.ok().build();
    }


}
