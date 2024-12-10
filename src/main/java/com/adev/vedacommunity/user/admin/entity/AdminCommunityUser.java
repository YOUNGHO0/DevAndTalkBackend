package com.adev.vedacommunity.user.admin.entity;

import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.Company;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class AdminCommunityUser extends CommunityUser {

    private String adminUserName;

    public AdminCommunityUser(String nickname, String email, Company company, String adminUserName) {
        super(nickname, email, company);
        authorities.add((GrantedAuthority) () -> "ROLE_ADMIN");
        this.adminUserName = adminUserName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }
}
