package com.adev.vedacommunity.user.dto.response;

import lombok.Getter;

@Getter
public class UserReadResponseDto {

    private long id;
    private String email;
    private String nickname;

    long vedaOrder;
}
