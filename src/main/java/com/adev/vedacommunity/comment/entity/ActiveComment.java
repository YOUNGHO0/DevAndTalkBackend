package com.adev.vedacommunity.comment.entity;

import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Immutable;

@Immutable
@Entity
@Getter
public class ActiveComment {

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

}
