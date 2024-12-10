package com.adev.vedacommunity.mapper;

import com.adev.vedacommunity.user.admin.dto.AdminUserListResponseDto;
import com.adev.vedacommunity.user.entity.CommunityUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminUserListResponseDto toAdminUserDto(CommunityUser user);
}
