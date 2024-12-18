package com.adev.vedacommunity.anoncomment.dto.read;

import com.adev.vedacommunity.user.dto.response.CommunityUserReadResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnonCommentReadResponseDto {

    long id;
    String commentContent;
    CommunityUserReadResponseDto commentAuthor;
    long articleId;
}
