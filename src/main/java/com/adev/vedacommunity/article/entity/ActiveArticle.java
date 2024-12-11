package com.adev.vedacommunity.article.entity;

import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import java.util.Objects;

@Entity
@Immutable  // 뷰는 읽기 전용이므로 이 어노테이션을 추가
@Getter // 생성한 뷰 이름을 테이블처럼 사용
public class ActiveArticle {


    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // 같은 객체인지 확인
        if (o == null || getClass() != o.getClass()) return false; // 클래스 타입 확인
        ActiveArticle that = (ActiveArticle) o;
        return id == that.id; // id가 동일한지 확인
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // id를 기반으로 hashCode 생성
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
