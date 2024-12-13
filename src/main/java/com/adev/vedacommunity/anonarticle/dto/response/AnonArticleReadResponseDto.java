package com.adev.vedacommunity.anonarticle.dto.response;

import com.adev.vedacommunity.user.dto.response.CommunityUserReadResponseDto;
import lombok.Getter;

@Getter
public class AnonArticleReadResponseDto {

    long viewCount;
    String title;
    String content;
    CommunityUserReadResponseDto author;
}
