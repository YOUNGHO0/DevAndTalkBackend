package com.adev.vedacommunity.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class ChildCommentCreateRequestDto {
    long parentId;
    String commentContent;
}
