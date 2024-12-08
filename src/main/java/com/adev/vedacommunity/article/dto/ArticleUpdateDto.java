package com.adev.vedacommunity.article.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ArticleUpdateDto {
    @NotBlank
    long id;
    @NotBlank
    String title;
    @NotBlank
    String content;

}
