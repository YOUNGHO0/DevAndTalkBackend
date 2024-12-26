package com.adev.vedacommunity.anoncomment.dto.read;

import com.adev.vedacommunity.user.dto.response.CommunityUserReadResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@AllArgsConstructor
public class AnonCommentDto {

    long id;
    LocalDateTime createdAt;
    String commentContent;
    @Setter
    List<AnonCommentDto> childCommentList;
    boolean author;
    boolean anonArticleAuthor;

    public AnonCommentDto(long id, String commentContent, LocalDateTime createdAt,boolean author, boolean anonArticleAuthor) {
        this.id = id;
        this.commentContent = commentContent;
        this.createdAt = createdAt;
        childCommentList = null;
        this.author = author;
        this.anonArticleAuthor = anonArticleAuthor;
    }

}
