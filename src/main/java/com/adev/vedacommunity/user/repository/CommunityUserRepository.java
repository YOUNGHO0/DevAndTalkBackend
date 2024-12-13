package com.adev.vedacommunity.user.repository;

import com.adev.vedacommunity.user.entity.CommunityUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityUserRepository extends JpaRepository<CommunityUser, Long> {

    Optional<CommunityUser> findByEmail(String email);


}
