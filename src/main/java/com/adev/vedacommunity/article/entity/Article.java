package com.adev.vedacommunity.article.entity;

import com.adev.vedacommunity.comment.entity.Comment;
import com.adev.vedacommunity.logging.BaseTimeEntity;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
public class Article extends BaseTimeEntity {


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Article that = (Article) o;
        return Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id); // id 기반으로 hashCode 생성
    }

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

    @ManyToOne(fetch = FetchType.LAZY)
    CommunityUserView author;

    public boolean canCreate(){
        return true;
    }

    public boolean canUpdate(String title, String content, CommunityUserView author ){
        if(!author.equals(this.author))
            throw new RuntimeException("Author does not match");
        return true;
    }


    public void update(String title, String content, CommunityUserView author ) {
        this.title = title;
        this.content = content;
    }

    public boolean canDelete(CommunityUserView author){
        if(!author.equals(this.author)) throw new RuntimeException("Author does not match current : "+ this.author.getId() + " " + "extra :" +author.getId());

        return true;
    }
    public void delete(){
        this.isDeleted = true;
    }
}
