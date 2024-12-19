package com.adev.vedacommunity.comment.repository;

import com.adev.vedacommunity.comment.entity.Comment;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByCommentAuthor(CommunityUserView user);

}
