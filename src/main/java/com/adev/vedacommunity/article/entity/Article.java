package com.adev.vedacommunity.article.entity;

import com.adev.vedacommunity.user.entity.CommunityUser;
import jakarta.persistence.*;

@Entity
public class Article {

    protected Article() {}

    public Article(String title, String content, CommunityUser author) {

        this.title = title;
        this.content = content;
        this.author = author;
        this.viewCount = 0;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String title;
    String content;
    long viewCount;

    @ManyToOne
    CommunityUser author;



    public void update(String title, String content, CommunityUser author ) {

        if(!author.equals(this.author))
            throw new RuntimeException("Author does not match");

        this.title = title;
        this.content = content;
    }

    public boolean canDelete(CommunityUser author){
        if(!author.equals(this.author)) throw new RuntimeException("Author does not match");

        return true;
    }
}
