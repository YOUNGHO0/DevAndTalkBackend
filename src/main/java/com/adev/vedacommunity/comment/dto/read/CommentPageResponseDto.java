package com.adev.vedacommunity.comment.dto.read;

import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.comment.entity.Comment;
import com.adev.vedacommunity.user.dto.response.CommunityUserReadResponseDto;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.mapper.CommunityUserMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@AllArgsConstructor
public class CommentPageResponseDto {

    List<CommentDto> commentList;


}
