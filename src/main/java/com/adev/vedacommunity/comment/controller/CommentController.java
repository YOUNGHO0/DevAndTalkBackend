package com.adev.vedacommunity.comment.controller;

import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.article.service.ArticleService;
import com.adev.vedacommunity.comment.dto.read.CommentDto;
import com.adev.vedacommunity.comment.dto.read.CommentPageResponseDto;
import com.adev.vedacommunity.comment.dto.read.CommentReadResponseDto;
import com.adev.vedacommunity.comment.dto.request.*;
import com.adev.vedacommunity.comment.entity.ActiveComment;
import com.adev.vedacommunity.comment.entity.Comment;
import com.adev.vedacommunity.comment.mapper.CommentMapper;
import com.adev.vedacommunity.comment.repository.ActiveCommentRepository;
import com.adev.vedacommunity.comment.repository.CommentRepository;
import com.adev.vedacommunity.comment.service.CommentService;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.mapper.CommunityUserMapper;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final ArticleService articleService;
    private final ActiveCommentRepository activeCommentRepository;
    private final CommentRepository commentRepository;
    private final CommunityUserMapper communityUserMapper;
    @PostMapping
    public ResponseEntity creatComment(@RequestBody CommentCreateRequestDto dto, @AuthenticationPrincipal CommunityUserView user){

        ActiveArticle article = articleService.read(dto.getArticleId()).orElseThrow(() -> new RuntimeException("No article found"));
        Comment comment = commentMapper.toComment("commentContent", user, article);
        commentService.createComment(comment);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/child")
    public ResponseEntity createChildComment(@RequestBody ChildCommentCreateRequestDto dto, @AuthenticationPrincipal CommunityUserView user){

        Comment parentComment =  commentRepository.findById(dto.getParentId()).orElseThrow(() -> new RuntimeException("No comment found"));
        if(parentComment.getParentComment() != null) throw new RuntimeException("not usual comment Request");
        Comment childComment = commentMapper.toChildComment(dto, parentComment, user, parentComment.getArticle());
        commentService.createComment(childComment);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity getChildCommentList(@RequestBody CommentPageRequestDto dto){

        List<ActiveComment> commentList = commentService.getCommentList(dto.getArticleId());
        List<CommentDto> commentDtoList = makeCommentDtos(commentList);
        return ResponseEntity.ok().body(commentDtoList);
    }

    private List<CommentDto> makeCommentDtos(List<ActiveComment> commentList) {



        Map<Long, CommentDto> parentCommentMap = new HashMap<>();
        Map<Long, List<CommentDto>> childComment = new HashMap<>();

        for(ActiveComment comment : commentList){

            if(comment.getParentCommentId() == null){
                CommentDto parent = new CommentDto(
                        comment.getId(),
                        comment.getCommentContent(),
                        communityUserMapper.toDto(comment.getCommentAuthor()),
                        comment.getCreatedDate()
                        );
                parentCommentMap.put(comment.getId(),parent);
            }
            else{
                List<CommentDto> comList = childComment.get(comment.getParentCommentId());
                if (comList == null) {
                    comList = new ArrayList<>();
                    childComment.put(comment.getParentCommentId(), comList); // 맵에 새 리스트를 저장
                }
                comList.add(
                        new CommentDto(comment.getId(),
                                comment.getCommentContent(),
                                communityUserMapper.toDto(comment.getCommentAuthor()),
                                comment.getCreatedDate()
                        ));

            }
        }

        List<CommentDto> commentDtoList  = new ArrayList<>();


        Set<Long> childKeys = childComment.keySet();
        for(Long key : childKeys){

            if(parentCommentMap.containsKey(key)){
                CommentDto parentCommentDto = parentCommentMap.get(key);
                List<CommentDto> childCommentList = childComment.get(key);
                parentCommentDto.setChildCommentList(childCommentList);
                childComment.remove(key);
                parentCommentMap.remove(key);
                commentDtoList.add(parentCommentDto);
            }
            else{
                CommentDto parentMockDto = new CommentDto(key, "삭제된 댓글입니다", null,null);
                List<CommentDto> childCommentList = childComment.get(key);
                childComment.remove(key);
                parentMockDto.setChildCommentList(childCommentList);
                commentDtoList.add(parentMockDto);
            }
        }
        for( CommentDto parentComment :parentCommentMap.values()){
            commentDtoList.add(parentComment);
        }
       commentDtoList.sort(Comparator.comparing(CommentDto::getId));

        return commentDtoList;
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
