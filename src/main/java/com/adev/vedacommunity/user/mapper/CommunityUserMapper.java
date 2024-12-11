package com.adev.vedacommunity.user.mapper;

import com.adev.vedacommunity.user.dto.response.CommunityUserReadResponseDto;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommunityUserMapper {

    CommunityUserReadResponseDto toDto(CommunityUserView communityUser);

}
