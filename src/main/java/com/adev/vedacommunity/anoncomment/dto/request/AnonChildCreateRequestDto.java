package com.adev.vedacommunity.anoncomment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class AnonChildCreateRequestDto {
    long parentId;
    @NotBlank(message = "내용을 입력해 주세요")
    String commentContent;
}
