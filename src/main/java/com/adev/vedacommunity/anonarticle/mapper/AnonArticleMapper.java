package com.adev.vedacommunity.anonarticle.mapper;

import com.adev.vedacommunity.anonarticle.dto.request.AnonArticleCreateRequestDto;
import com.adev.vedacommunity.anonarticle.dto.request.AnonArticleReadRequestDto;
import com.adev.vedacommunity.anonarticle.dto.request.AnonArticleUpdateRequestDto;
import com.adev.vedacommunity.anonarticle.dto.response.AnonArticleReadResponseDto;
import com.adev.vedacommunity.anonarticle.entity.AnonArticle;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnonArticleMapper {

    AnonArticleReadResponseDto toReadDto(AnonArticle anonArticle);
    AnonArticle toAnonArticle(AnonArticleCreateRequestDto dto, CommunityUserView author);
    AnonArticle toAnonArticle(AnonArticleUpdateRequestDto dto, CommunityUserView author);

}
