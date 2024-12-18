package com.adev.vedacommunity.comment.repository;

import com.adev.vedacommunity.comment.entity.ActiveComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActiveCommentRepository extends JpaRepository<ActiveComment, Long> {

    List<ActiveComment> findAllByArticleId(Long articleID);

}
