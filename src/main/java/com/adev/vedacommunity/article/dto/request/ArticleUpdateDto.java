package com.adev.vedacommunity.article.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleUpdateDto {
    long id;

    @NotBlank(message = "제목을 입력해 주세요")
    String title;
    @NotBlank(message = "내용을 입력해 주세요")
    String content;

}
