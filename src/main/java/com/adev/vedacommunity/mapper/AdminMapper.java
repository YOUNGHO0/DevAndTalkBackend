package com.adev.vedacommunity.mapper;

import com.adev.vedacommunity.admin.dto.AdminUserListResponseDto;
import com.adev.vedacommunity.user.entity.CommunityUser;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;


@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminUserListResponseDto toAdminUserDto(CommunityUser user);

    default Page<AdminUserListResponseDto> toAdminPageReadDto(Page<CommunityUser> communityUsers){

        return communityUsers.map(this::toAdminUserDto);
    }
}
