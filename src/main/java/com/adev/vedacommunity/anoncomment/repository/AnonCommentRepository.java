package com.adev.vedacommunity.anoncomment.repository;

import com.adev.vedacommunity.anoncomment.entity.AnonComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonCommentRepository extends JpaRepository<AnonComment, Long> {

}
