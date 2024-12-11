package com.adev.vedacommunity.article.entity;

import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable  // 뷰는 읽기 전용이므로 이 어노테이션을 추가
@Getter // 생성한 뷰 이름을 테이블처럼 사용
public class ActiveArticle {

    protected ActiveArticle(){};
    @Id
    long id;

    String title;
    String content;
    long viewCount = 0;

    @ManyToOne
    CommunityUserView author;

}
