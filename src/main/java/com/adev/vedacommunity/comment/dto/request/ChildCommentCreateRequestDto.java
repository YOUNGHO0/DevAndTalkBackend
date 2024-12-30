package com.adev.vedacommunity.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class ChildCommentCreateRequestDto {

    long parentId;

    @NotBlank(message = "내용을 입력해 주세요")
    String commentContent;
}
