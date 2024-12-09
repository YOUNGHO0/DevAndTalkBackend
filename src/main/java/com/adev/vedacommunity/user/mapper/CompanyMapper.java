package com.adev.vedacommunity.user.mapper;

import com.adev.vedacommunity.user.dto.response.CompanyReadResponseDto;
import com.adev.vedacommunity.user.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyReadResponseDto toDto(Company company);
}
