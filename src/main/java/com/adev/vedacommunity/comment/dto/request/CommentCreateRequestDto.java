package com.adev.vedacommunity.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCreateRequestDto {

    private long articleId;
    @NotBlank(message = "내용을 입력해 주세요")
    private String commentContent;

}
