package com.adev.vedacommunity.anonarticle.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class ActiveAnonArticle {

    protected ActiveAnonArticle(){};

    @Id
    long id;
    String title;
    String content;
    long viewCount;

}
