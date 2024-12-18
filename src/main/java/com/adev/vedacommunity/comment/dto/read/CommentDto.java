package com.adev.vedacommunity.comment.dto.read;

import com.adev.vedacommunity.user.dto.response.CommunityUserReadResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@AllArgsConstructor
public class CommentDto {

    long id;
    LocalDateTime createdAt;
    String commentContent;
    CommunityUserReadResponseDto author;
    @Setter
    List<CommentDto> childCommentList;

    public CommentDto( long id, String commentContent, CommunityUserReadResponseDto dto, LocalDateTime createdAt) {
        this.id = id;
        this.commentContent = commentContent;
        this.author = dto;
        this.createdAt = createdAt;
        childCommentList = null;
    }

}
