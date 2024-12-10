package com.adev.vedacommunity.article.mapper;

import com.adev.vedacommunity.article.dto.request.ArticleCreateDto;
import com.adev.vedacommunity.article.dto.request.ArticleReadDto;
import com.adev.vedacommunity.article.dto.response.ArticleReadResponseDto;
import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.user.dto.response.CommunityUserReadResponseDto;
import com.adev.vedacommunity.user.entity.CommunityUser;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
@Mapper(componentModel = "spring")
public interface ArticleMapper {

    Article toArticle(ArticleCreateDto dto, CommunityUser author);
    ArticleReadResponseDto toArticleReadDto(Article article);
    // Page<Article>을 Page<ArticleReadResponseDto>로 변환
    default Page<ArticleReadResponseDto> toReadPageDto(Page<Article> articles) {
        return articles.map(this::toArticleReadDto);
    }
}
