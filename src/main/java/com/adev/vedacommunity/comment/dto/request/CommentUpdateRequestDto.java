package com.adev.vedacommunity.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class CommentUpdateRequestDto {

    long commentId;
    String content;
}
