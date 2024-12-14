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
    public boolean canUpdate(CommunityUserView requestUser){
        if(this.commentAuthor.equals(requestUser)){
            return true;
        }
        return false;
    }
    public boolean canDelete(CommunityUserView requestUser){
        return true;
    }
    public void delete(){
        this.isDeleted = true;
    }
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
