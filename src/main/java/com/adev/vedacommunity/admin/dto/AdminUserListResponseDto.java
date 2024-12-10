package com.adev.vedacommunity.admin.dto;

import com.adev.vedacommunity.user.dto.response.CompanyReadResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class AdminUserListResponseDto {

    private long id;
    private String nickname;
    String email;
    long vedaOrder;
    CompanyReadResponseDto company;
}
