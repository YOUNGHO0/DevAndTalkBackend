package com.adev.vedacommunity.anonarticle.entity;

import com.adev.vedacommunity.logging.BaseTimeEntity;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Entity
@Getter
@Immutable
public class ActiveAnonArticle extends BaseTimeEntity {

    protected ActiveAnonArticle(){};

    @Id
    long id;
    String title;
    String content;
    long viewCount;
    @ManyToOne(fetch = FetchType.LAZY)
    CommunityUserView author;

}
