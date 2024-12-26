package com.adev.vedacommunity.anoncomment.entity;

import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.logging.BaseTimeEntity;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Immutable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Immutable
@Entity
@Getter
public class ActiveAnonComment extends BaseTimeEntity {


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        ActiveAnonComment that = (ActiveAnonComment) o;
        return Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id); // id 기반으로 hashCode 생성
    }

    protected ActiveAnonComment(){};
    @Id
    long id;
    @ManyToOne(fetch = FetchType.LAZY)
    CommunityUserView commentAuthor;
    String commentContent;
    @ManyToOne
    ActiveArticle article;
    @ManyToOne
    ActiveAnonComment parentComment;
    @Column(name = "parent_comment_id", insertable = false, updatable = false) // 외래 키 값 직접 접근
    private Long parentCommentId;
    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    List<ActiveAnonComment> childComments = new ArrayList<>();

}
