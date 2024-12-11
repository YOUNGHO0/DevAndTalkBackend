package com.adev.vedacommunity.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Entity
@Immutable  // 뷰는 읽기 전용이므로 이 어노테이션을 추가
@Getter // 생성한 뷰 이름을 테이블처럼 사용
public class CommunityUserView {

    protected CommunityUserView() {
    }

    @Transient
    protected Collection<GrantedAuthority> authorities;

    @Id
    private long id;
    private String email;
    private String nickname;
    private long vedaOrder;
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Getter, Setter 생략 (Lombok을 사용한 경우)
}