package com.adev.vedacommunity.comment.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentUpdateRequestDto {

    long commentId;
    String content;
}
