package com.adev.vedacommunity.anoncomment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnonCommentUpdateRequestDto {

    long commentId;
    @NotBlank(message = "내용을 입력해 주세요")
    String content;
}
