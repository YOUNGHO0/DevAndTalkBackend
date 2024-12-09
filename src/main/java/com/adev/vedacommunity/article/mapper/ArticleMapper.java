package com.adev.vedacommunity.article.mapper;

import com.adev.vedacommunity.article.dto.request.ArticleCreateDto;
import com.adev.vedacommunity.article.dto.request.ArticleReadDto;
import com.adev.vedacommunity.article.dto.response.ArticleReadResponseDto;
import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.user.dto.response.CommunityUserReadResponseDto;
import com.adev.vedacommunity.user.entity.CommunityUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    Article toArticle(ArticleCreateDto dto, CommunityUser author);
    ArticleReadResponseDto toArticleReadDto(Article article);
}
