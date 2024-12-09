package com.adev.vedacommunity.article.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ArticleCreateDto {


    @NotBlank
    private String title;
    @NotBlank
    private String content;


}
