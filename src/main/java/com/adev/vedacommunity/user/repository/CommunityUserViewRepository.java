package com.adev.vedacommunity.user.repository;

import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  CommunityUserViewRepository extends JpaRepository<CommunityUserView, Long> {
    Optional<CommunityUserView> findByEmail(String email);
}
