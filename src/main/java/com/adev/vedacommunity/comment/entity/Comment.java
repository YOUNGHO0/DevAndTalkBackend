package com.adev.vedacommunity.comment.entity;


import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import jakarta.persistence.*;

@Entity
public class Comment {

    public Comment() {

    }

    public Comment(String commentContent, CommunityUserView commentAuthor, ActiveArticle article ){
        this.commentContent = commentContent;
        this.commentAuthor = commentAuthor;
        this.article = article;
    }

    public boolean canCreate(){

        return true;
    }
    public boolean canUpdate;

    public void update (String commentContent){
        this.commentContent = commentContent;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String commentContent;
    boolean isDeleted = false;
    @ManyToOne(fetch = FetchType.LAZY)
    CommunityUserView commentAuthor;
    @ManyToOne(fetch = FetchType.LAZY)
    ActiveArticle article;
    @ManyToOne(fetch = FetchType.LAZY)
    Comment parentComment;

}
