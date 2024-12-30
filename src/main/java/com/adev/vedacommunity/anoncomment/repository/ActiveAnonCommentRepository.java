package com.adev.vedacommunity.anoncomment.repository;

import com.adev.vedacommunity.anoncomment.entity.ActiveAnonComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActiveAnonCommentRepository extends JpaRepository<ActiveAnonComment, Long> {

    List<ActiveAnonComment> findAllByAnonArticleId(Long articleID);

}
