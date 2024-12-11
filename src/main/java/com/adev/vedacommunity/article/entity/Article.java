package com.adev.vedacommunity.article.entity;

import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Article {

    protected Article() {}

    public Article(String title, String content, CommunityUserView author) {

        this.title = title;
        this.content = content;
        this.author = author;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String title;
    String content;
    long viewCount = 0;
    boolean isDeleted = false;

    @ManyToOne
    CommunityUserView author;

    public boolean canCreate(){
        return true;
    }

    public boolean canUpdate(String title, String content, CommunityUser author ){
        if(!author.equals(this.author))
            throw new RuntimeException("Author does not match");
        return true;
    }


    public void update(String title, String content, CommunityUser author ) {
        this.title = title;
        this.content = content;
    }

    public boolean canDelete(CommunityUser author){
        if(!author.equals(this.author)) throw new RuntimeException("Author does not match");

        return true;
    }
    public void delete(){
        this.isDeleted = true;
    }
}
