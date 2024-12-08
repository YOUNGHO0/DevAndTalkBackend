package com.adev.vedacommunity.comment.entity;


import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.user.entity.CommunityUser;
import jakarta.persistence.*;

@Entity
public class Comment {

    public Comment() {

    }

    Comment(String commentContent, CommunityUser commentAuthor, Article article ){
        this.commentContent = commentContent;
        this.commentAuthor = commentAuthor;
        this.article = article;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String commentContent;

    @ManyToOne
    CommunityUser commentAuthor;

    @ManyToOne
    Article article;

}
