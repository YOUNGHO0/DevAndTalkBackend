package com.adev.vedacommunity.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCreateRequestDto {

    private long articleId;
    private String commentContent;

}
