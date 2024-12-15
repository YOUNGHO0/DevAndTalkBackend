package com.adev.vedacommunity.comment.mapper;

import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.comment.dto.read.CommentReadResponseDto;
import com.adev.vedacommunity.comment.dto.request.ChildCommentCreateRequestDto;
import com.adev.vedacommunity.comment.dto.request.CommentCreateRequestDto;
import com.adev.vedacommunity.comment.entity.ActiveComment;
import com.adev.vedacommunity.comment.entity.Comment;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "articleId", source = "comment.article.id")
    CommentReadResponseDto toDto(ActiveComment comment);
    default Comment toComment(String commentContent, CommunityUserView commentAuthor, ActiveArticle article){
        return new Comment(commentContent,commentAuthor,article);
    }

    default Comment toChildComment(ChildCommentCreateRequestDto dto, Comment parentComment, CommunityUserView user,ActiveArticle article){
        return new Comment(dto.getCommentContent(),user,article,parentComment);
    }
}
