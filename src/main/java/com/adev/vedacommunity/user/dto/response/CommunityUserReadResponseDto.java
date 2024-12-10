package com.adev.vedacommunity.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommunityUserReadResponseDto {

    private long id;
    private String nickname;
    long vedaOrder;
    CompanyReadResponseDto company;
}
