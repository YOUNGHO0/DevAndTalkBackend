package com.adev.vedacommunity.admin.entity;

import com.adev.vedacommunity.user.entity.CommunityUser;
import jakarta.persistence.Entity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Entity
public class AdminCommunityUser extends CommunityUser {

    protected AdminCommunityUser() {

    }

    @Override
    public String canTest(CommunityUser user) {

        AdminCommunityUser test = (AdminCommunityUser) user;
        return test.getAdminUserName();

    }

    private String adminUserName;

    public AdminCommunityUser(String email, String nickName, String adminUserName) {
        super(email, nickName);
        this.adminUserName = adminUserName;
        authorities.add((GrantedAuthority) () -> "ROLE_ADMIN");
        this.adminUserName = adminUserName;
    }


}
