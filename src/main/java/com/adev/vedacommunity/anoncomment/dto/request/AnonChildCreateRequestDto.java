package com.adev.vedacommunity.anoncomment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class AnonChildCreateRequestDto {
    long parentId;
    String commentContent;
}
