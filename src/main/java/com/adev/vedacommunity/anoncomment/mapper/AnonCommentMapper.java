package com.adev.vedacommunity.anoncomment.mapper;

import com.adev.vedacommunity.anonarticle.entity.ActiveAnonArticle;
import com.adev.vedacommunity.anoncomment.dto.read.AnonCommentReadResponseDto;
import com.adev.vedacommunity.anoncomment.dto.request.AnonChildCreateRequestDto;
import com.adev.vedacommunity.anoncomment.entity.ActiveAnonComment;
import com.adev.vedacommunity.anoncomment.entity.AnonComment;
import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnonCommentMapper {

    @Mapping(target = "articleId", source = "AnonComment.article.id")
    AnonCommentReadResponseDto toDto(ActiveAnonComment AnonComment);
    default AnonComment toComment(String commentContent, CommunityUserView commentAuthor, ActiveAnonArticle article){
        return new AnonComment(commentContent,commentAuthor,article);
    }

    default AnonComment toChildComment(AnonChildCreateRequestDto dto, AnonComment parentComment, CommunityUserView user, ActiveAnonArticle article){
        return new AnonComment(dto.getCommentContent(),user,article,parentComment);
    }
}
