package com.adev.vedacommunity.admin.mapper;

import com.adev.vedacommunity.admin.dto.AdminUserListResponseDto;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;


@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminUserListResponseDto toAdminUserDto(CommunityUserView user);

    default Page<AdminUserListResponseDto> toAdminPageReadDto(Page<CommunityUserView> communityUsers){

        return communityUsers.map(this::toAdminUserDto);
    }
}
