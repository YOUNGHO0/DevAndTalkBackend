package com.adev.vedacommunity.user.entity;

import com.adev.vedacommunity.logging.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
public class CommunityUser extends BaseTimeEntity {
    @Transient
    protected Collection<GrantedAuthority> authorities;
    // 기본 생성자 (JPA에서 필수)
    protected CommunityUser() {

    }

    public CommunityUser(String email, String nickName){

        this.email = email;
        this.vedaOrder = 1;
        this.company = null;

        authorities = new ArrayList<>();
        authorities.add((GrantedAuthority) () -> "ROLE_USER");

    }

    // 생성자 오버로딩
    public CommunityUser(String nickname, String email, Company company) {
        this.nickname = nickname;
        this.email = email;
        this.company = company;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private long id;
    private String email;
    private String nickname;

    @ManyToOne
    private Company company;

    long vedaOrder;

    // ToString 메서드 (디버깅용)
    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", company=" + (company != null ? "Company{id=" + company.getId() + ", name='" + company.getName() + "'}" : "null") +
                '}';
    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


}
