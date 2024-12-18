package com.adev.vedacommunity.anoncomment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnonCommentCreateRequestDto {

    private long articleId;
    private String commentContent;

}
