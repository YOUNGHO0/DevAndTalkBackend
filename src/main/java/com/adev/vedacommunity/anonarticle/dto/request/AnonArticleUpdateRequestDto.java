package com.adev.vedacommunity.anonarticle.dto.request;

import com.adev.vedacommunity.anonarticle.entity.AnonArticle;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@AllArgsConstructor
public class AnonArticleUpdateRequestDto {

    long id;
    @NotBlank(message = "제목을 입력해 주세요")
    String title;
    @NotBlank(message = "내용을 입력해 주세요")
    String content;

}
