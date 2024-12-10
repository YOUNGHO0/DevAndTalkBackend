package com.adev.vedacommunity.admin.entity;

import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.Company;
import jakarta.persistence.Entity;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Entity
public class AdminCommunityUser extends CommunityUser {

    protected AdminCommunityUser() {

    }

    private String adminUserName;

    public AdminCommunityUser(String email, String nickName, String adminUserName) {
        super(email, nickName);
        this.adminUserName = adminUserName;
        authorities.add((GrantedAuthority) () -> "ROLE_ADMIN");
        this.adminUserName = adminUserName;
    }


}
