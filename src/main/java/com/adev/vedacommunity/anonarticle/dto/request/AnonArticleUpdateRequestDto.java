package com.adev.vedacommunity.anonarticle.dto.request;

import com.adev.vedacommunity.anonarticle.entity.AnonArticle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@AllArgsConstructor
public class AnonArticleUpdateRequestDto {

    long id;
    String title;
    String content;

}
