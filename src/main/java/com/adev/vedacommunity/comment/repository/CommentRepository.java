package com.adev.vedacommunity.comment.repository;

import com.adev.vedacommunity.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
