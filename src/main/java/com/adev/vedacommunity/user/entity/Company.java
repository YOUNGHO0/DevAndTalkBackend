package com.adev.vedacommunity.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<CommunityUser> employees; // 해당 회사에 속한 사람들
}
