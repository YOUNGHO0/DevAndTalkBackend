package com.adev.vedacommunity.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class CommentUpdateRequestDto {

    long commentId;

    @NotBlank(message = "내용을 입력해 주세요")
    String content;
}
