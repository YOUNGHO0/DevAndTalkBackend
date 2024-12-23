package com.adev.vedacommunity.user.controller;

import com.adev.vedacommunity.anonarticle.service.AnonArticleService;
import com.adev.vedacommunity.anoncomment.service.AnonCommentService;
import com.adev.vedacommunity.article.service.ArticleService;
import com.adev.vedacommunity.comment.service.CommentService;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.mapper.CommunityUserMapper;
import com.adev.vedacommunity.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ArticleService articleService;
    private final AnonArticleService anonArticleService;
    private final CommentService commentService;
    private final AnonCommentService anonCommentService;
    private final CommunityUserMapper communityUserMapper;

    @GetMapping
    public ResponseEntity getUserInfo(@AuthenticationPrincipal CommunityUserView user) {

        return ResponseEntity.ok(communityUserMapper.toDto(user));
    }

    @DeleteMapping
    public void deleteUser(@AuthenticationPrincipal CommunityUserView communityUserView) {
        userService.deleteUser(communityUserView);
        articleService.deleteAll(communityUserView);
        anonArticleService.deleteAlL(communityUserView);
        anonCommentService.deleteAll(communityUserView);
        commentService.deleteAll(communityUserView);
    }
}
