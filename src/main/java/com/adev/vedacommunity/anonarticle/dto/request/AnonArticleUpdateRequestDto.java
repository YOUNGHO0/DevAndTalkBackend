package com.adev.vedacommunity.anonarticle.dto.request;

import lombok.Getter;

@Getter
public class AnonArticleUpdateRequestDto {

    long anonArticleId;
    String title;
    String content;

}
