package com.adev.vedacommunity.article.mapper;

import com.adev.vedacommunity.article.dto.request.ArticleCreateDto;
import com.adev.vedacommunity.article.dto.response.ArticleReadResponseDto;
import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
@Mapper(componentModel = "spring")
public interface ArticleMapper {

    Article toArticle(ArticleCreateDto dto, CommunityUserView author);
    ArticleReadResponseDto toArticleReadDto(ActiveArticle article);
    // Page<Article>을 Page<ArticleReadResponseDto>로 변환
    default Page<ArticleReadResponseDto> toReadPageDto(Page<ActiveArticle> articles) {
        return articles.map(this::toArticleReadDto);
    }
}
