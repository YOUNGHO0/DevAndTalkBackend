package com.adev.vedacommunity.article.dto.response;

import com.adev.vedacommunity.user.dto.response.CommunityUserReadResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ArticleReadResponseDto {

    String title;
    String content;
    long viewCount;
    CommunityUserReadResponseDto author;

}
