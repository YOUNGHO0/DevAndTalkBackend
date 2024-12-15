package com.adev.vedacommunity.comment.controller;

import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.article.service.ArticleService;
import com.adev.vedacommunity.comment.dto.read.CommentReadResponseDto;
import com.adev.vedacommunity.comment.dto.request.*;
import com.adev.vedacommunity.comment.entity.ActiveComment;
import com.adev.vedacommunity.comment.entity.Comment;
import com.adev.vedacommunity.comment.mapper.CommentMapper;
import com.adev.vedacommunity.comment.repository.ActiveCommentRepository;
import com.adev.vedacommunity.comment.repository.CommentRepository;
import com.adev.vedacommunity.comment.service.CommentService;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final ArticleService articleService;
    private final ActiveCommentRepository activeCommentRepository;
    @PostMapping
    public ResponseEntity creatComment(@RequestBody CommentCreateRequestDto dto, @AuthenticationPrincipal CommunityUserView user){

        ActiveArticle article = articleService.read(dto.getArticleId()).orElseThrow(() -> new RuntimeException("No article found"));
        Comment comment = commentMapper.toComment("commentContent", user, article);
        commentService.createComment(comment);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/child")
    public ResponseEntity createChildComment(@RequestBody ChildCommentCreateRequestDto dto, @AuthenticationPrincipal CommunityUserView user){

        ActiveComment parentComment = activeCommentRepository.findById(dto.getParentId()).orElseThrow(() -> new RuntimeException("No comment found"));
        Comment childComment = commentMapper.toChildComment(dto, parentComment, user, parentComment.getArticle());
        commentService.createComment(childComment);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getComments(@RequestBody CommentReadRequestDto dto){
        ActiveComment comment = commentService.readComment(dto.getCommentId());
        CommentReadResponseDto commentReadResponseDto = commentMapper.toDto(comment);
        return ResponseEntity.ok(commentReadResponseDto);
    }
    @PatchMapping
    public ResponseEntity updateComment(@RequestBody CommentUpdateRequestDto dto, @AuthenticationPrincipal CommunityUserView user){

        commentService.updateComment(dto.getCommentId(),dto.getContent(),user);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping
    public ResponseEntity deleteComment(@RequestBody CommentDeleteRequestDto dto, @AuthenticationPrincipal CommunityUserView user){
        commentService.deleteComment(dto.getCommentId(),user);
        return ResponseEntity.ok().build();
    }

}
