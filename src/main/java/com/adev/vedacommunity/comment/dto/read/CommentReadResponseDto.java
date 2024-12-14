package com.adev.vedacommunity.comment.dto.read;

import com.adev.vedacommunity.user.dto.response.CommunityUserReadResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentReadResponseDto {

    long id;
    String commentContent;
    CommunityUserReadResponseDto commentAuthor;
    long articleId;
}
