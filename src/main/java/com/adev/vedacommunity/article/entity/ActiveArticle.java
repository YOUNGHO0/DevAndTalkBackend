package com.adev.vedacommunity.article.entity;

import com.adev.vedacommunity.logging.BaseTimeEntity;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Immutable;
import java.util.Objects;

@Entity
@Immutable  // 뷰는 읽기 전용이므로 이 어노테이션을 추가
@Getter // 생성한 뷰 이름을 테이블처럼 사용
public class ActiveArticle extends BaseTimeEntity {


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        ActiveArticle that = (ActiveArticle) o;
        return Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id); // id 기반으로 hashCode 생성
    }

    protected ActiveArticle(){};
    @Id
    long id;

    String title;
    String content;
    long viewCount = 0;

    @ManyToOne
    CommunityUserView author;





}
