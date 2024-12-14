package com.adev.vedacommunity.anonarticle.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Entity
@Getter
@Immutable
public class ActiveAnonArticle {

    protected ActiveAnonArticle(){};

    @Id
    long id;
    String title;
    String content;
    long viewCount;

}
