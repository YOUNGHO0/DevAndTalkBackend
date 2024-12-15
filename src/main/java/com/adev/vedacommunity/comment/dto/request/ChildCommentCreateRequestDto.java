package com.adev.vedacommunity.comment.dto.request;

import lombok.Getter;

@Getter
public class ChildCommentCreateRequestDto {
    long parentId;
    String commentContent;
}
