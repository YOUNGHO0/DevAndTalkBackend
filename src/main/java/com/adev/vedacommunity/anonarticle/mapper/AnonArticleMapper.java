package com.adev.vedacommunity.anonarticle.mapper;

import com.adev.vedacommunity.anonarticle.dto.request.AnonArticleCreateRequestDto;
import com.adev.vedacommunity.anonarticle.dto.response.AnonArticleReadResponseDto;
import com.adev.vedacommunity.anonarticle.entity.ActiveAnonArticle;
import com.adev.vedacommunity.anonarticle.entity.AnonArticle;
import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface AnonArticleMapper {

    default AnonArticleReadResponseDto toReadDto(ActiveAnonArticle anonArticle, CommunityUserView user){
        boolean isAuthor = false;
        if(anonArticle.getAuthor().equals(user)){
            isAuthor= true;
        }
        return new AnonArticleReadResponseDto(anonArticle.getId(),anonArticle.getCreatedDate(),anonArticle.getTitle(),anonArticle.getContent(),anonArticle.getViewCount(),isAuthor);
    }
    AnonArticle toAnonArticle(AnonArticleCreateRequestDto dto, CommunityUserView author);
    default Page<AnonArticleReadResponseDto> toAnonArticleReadResponseDto(Page<ActiveAnonArticle> activeArticle, CommunityUserView user){
        return activeArticle.map(activeAnonArticle -> this.toReadDto(activeAnonArticle,user));
    }

}
