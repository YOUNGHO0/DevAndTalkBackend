package com.adev.vedacommunity.user.entity;

import com.adev.vedacommunity.logging.BaseTimeEntity;
import com.adev.vedacommunity.user.role.CommunityUserRole;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.Hibernate;
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
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        CommunityUserView that = (CommunityUserView) o;
        return Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id); // id 기반으로 hashCode 생성
    }



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

    @Enumerated(EnumType.STRING)
    private CommunityUserRole role;
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>() {{
            add(() -> role.toString());
        }};
    }

    // Getter, Setter 생략 (Lombok을 사용한 경우)
}