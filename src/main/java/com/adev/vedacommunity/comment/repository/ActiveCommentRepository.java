package com.adev.vedacommunity.comment.repository;

import com.adev.vedacommunity.comment.entity.ActiveComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveCommentRepository extends JpaRepository<ActiveComment, Long> {
}
