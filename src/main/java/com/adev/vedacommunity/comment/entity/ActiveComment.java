package com.adev.vedacommunity.comment.entity;

import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Immutable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Immutable
@Entity
@Getter
public class ActiveComment {


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        ActiveComment that = (ActiveComment) o;
        return Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id); // id 기반으로 hashCode 생성
    }

    protected ActiveComment(){};
    @Id
    long id;

    String commentContent;
    @ManyToOne
    CommunityUserView commentAuthor;
    @ManyToOne
    ActiveArticle article;
    @ManyToOne
    ActiveComment parentComment;
    @Column(name = "parent_comment_id", insertable = false, updatable = false) // 외래 키 값 직접 접근
    private Long parentCommentId;
    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    List<ActiveComment> childComments = new ArrayList<>();

}
