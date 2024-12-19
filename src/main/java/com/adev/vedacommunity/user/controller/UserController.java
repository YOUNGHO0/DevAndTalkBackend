package com.adev.vedacommunity.user.controller;

import com.adev.vedacommunity.anonarticle.service.AnonArticleService;
import com.adev.vedacommunity.anoncomment.service.AnonCommentService;
import com.adev.vedacommunity.article.service.ArticleService;
import com.adev.vedacommunity.comment.service.CommentService;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    ArticleService articleService;
    @Autowired
    AnonArticleService anonArticleService;
    @Autowired
    CommentService commentService;
    @Autowired
    AnonCommentService anonCommentService;

    @DeleteMapping
    public void deleteUser(@AuthenticationPrincipal CommunityUserView communityUserView) {
        userService.deleteUser(communityUserView);
        articleService.deleteAll(communityUserView);
        anonArticleService.deleteAlL(communityUserView);
        anonCommentService.deleteAll(communityUserView);
        commentService.deleteAll(communityUserView);
    }
}
