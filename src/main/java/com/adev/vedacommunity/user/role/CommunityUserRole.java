package com.adev.vedacommunity.user.role;

public enum CommunityUserRole {
    ROLE_USER("사용자 권한"),
    ROLE_ADMIN("관리자 권한"),
    ROLE_TEMP("임시 사용자 권한");

    private final String description;

    CommunityUserRole(String s) {
        description = s;
    }

    // 권한 설명 가져오기
    public String getDescription() {
        return description;
    }
}
