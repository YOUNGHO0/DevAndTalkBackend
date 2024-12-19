package com.adev.vedacommunity.comment.service;

import com.adev.vedacommunity.comment.entity.ActiveComment;
import com.adev.vedacommunity.comment.entity.Comment;
import com.adev.vedacommunity.comment.repository.ActiveCommentRepository;
import com.adev.vedacommunity.comment.repository.CommentRepository;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ActiveCommentRepository activeCommentRepository;

    public void createComment(Comment comment) {

        if(comment.canCreate()){
            commentRepository.save(comment);
        }
    }

    public ActiveComment readComment(long commentId){
        return activeCommentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("No comment found with id: " + commentId));
    }

    public void updateComment(long commentId, String commentContent, CommunityUserView user){
        commentRepository.findById(commentId).ifPresent(comment -> {
            if(comment.canUpdate(user)){
                comment.update(commentContent);
            }
        });
    }

    public void deleteComment(long commentId,CommunityUserView user){
        commentRepository.findById(commentId).ifPresent(comment ->{
            if(comment.canDelete(user)){
                comment.delete();
            }
        });
    }

    public List<ActiveComment> getCommentList(long articleId){
        List<ActiveComment> commentList = activeCommentRepository.findAllByArticleId(articleId);
        return commentList;
    }

    public void deleteAll(CommunityUserView user){
        commentRepository.findByCommentAuthor(user).forEach(comment -> {
            if(comment.canDelete(user)){
                comment.delete();
            }
        });
    }

}
