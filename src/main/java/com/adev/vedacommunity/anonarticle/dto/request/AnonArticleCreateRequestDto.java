package com.adev.vedacommunity.anonarticle.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class AnonArticleCreateRequestDto {

    @NotBlank(message = "제목을 입력해 주세요")
    String title;
    @NotBlank(message = "내용을 입력해 주세요")
    String content;

}
