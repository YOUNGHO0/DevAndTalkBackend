package com.adev.vedacommunity.anonarticle.dto.response;

import com.adev.vedacommunity.user.dto.response.CommunityUserReadResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class AnonArticleReadResponseDto {

    String title;
    String content;
    long viewCount;

}
