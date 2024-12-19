package com.adev.vedacommunity.anoncomment.repository;

import com.adev.vedacommunity.anoncomment.entity.AnonComment;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnonCommentRepository extends JpaRepository<AnonComment, Long> {

    List<AnonComment> findByCommentAuthor(CommunityUserView commentAuthor);
}
