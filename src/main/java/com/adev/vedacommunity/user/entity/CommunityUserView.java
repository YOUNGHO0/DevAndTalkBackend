package com.adev.vedacommunity.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Immutable  // 뷰는 읽기 전용이므로 이 어노테이션을 추가
@Getter // 생성한 뷰 이름을 테이블처럼 사용
public class CommunityUserView {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // 같은 객체인지 확인
        if (o == null || getClass() != o.getClass()) return false; // 클래스 타입 확인
        CommunityUserView that = (CommunityUserView) o;
        return id == that.id; // id가 동일한지 확인
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // id를 기반으로 hashCode 생성
    }

    protected CommunityUserView() {
        authorities = new ArrayList<>();
        authorities.add((GrantedAuthority) () -> "ROLE_USER");
    }

    @Transient
    Collection<GrantedAuthority> authorities;
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