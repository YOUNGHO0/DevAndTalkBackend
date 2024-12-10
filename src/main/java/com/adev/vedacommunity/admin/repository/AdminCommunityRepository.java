package com.adev.vedacommunity.admin.repository;

import com.adev.vedacommunity.admin.entity.AdminCommunityUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminCommunityRepository extends JpaRepository<AdminCommunityUser, Long> {
}
