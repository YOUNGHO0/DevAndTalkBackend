package com.adev.vedacommunity.anoncomment.dto.read;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class AnonCommentPageResponseDto {

    List<AnonCommentDto> commentList;


}
