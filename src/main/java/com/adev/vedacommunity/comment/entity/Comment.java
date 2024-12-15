    package com.adev.vedacommunity.comment.entity;


    import com.adev.vedacommunity.article.entity.ActiveArticle;
    import com.adev.vedacommunity.logging.BaseTimeEntity;
    import com.adev.vedacommunity.user.entity.CommunityUserView;
    import jakarta.persistence.*;
    import lombok.Setter;

    import java.util.ArrayList;
    import java.util.List;

    @Entity
    public class Comment extends BaseTimeEntity {

        public Comment() {}

        public Comment(String commentContent, CommunityUserView commentAuthor, ActiveArticle article ){
            this.commentContent = commentContent;
            this.commentAuthor = commentAuthor;
            this.article = article;
        }
        public Comment(String commentContent, CommunityUserView commentAuthor, ActiveArticle article, ActiveComment parentComment ){
            this.commentContent = commentContent;
            this.commentAuthor = commentAuthor;
            this.article = article;
            this.parentComment = parentComment;
        }

        @OneToMany(mappedBy = "parentComment",fetch = FetchType.LAZY)
        List<Comment> commentList = new ArrayList<>();


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
            if(!this.commentAuthor.equals(requestUser)) throw new RuntimeException("Author not match");
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
        ActiveComment parentComment;

    }
