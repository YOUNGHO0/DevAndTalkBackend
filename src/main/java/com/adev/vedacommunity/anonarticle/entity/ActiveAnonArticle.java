package com.adev.vedacommunity.anonarticle.entity;

import com.adev.vedacommunity.logging.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

}
