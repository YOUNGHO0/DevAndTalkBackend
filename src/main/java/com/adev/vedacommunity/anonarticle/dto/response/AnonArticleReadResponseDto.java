package com.adev.vedacommunity.anonarticle.dto.response;

import com.adev.vedacommunity.user.dto.response.CommunityUserReadResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
public class AnonArticleReadResponseDto {
    long id;
    LocalDateTime createdDate;
    String title;
    String content;
    long viewCount;

}
