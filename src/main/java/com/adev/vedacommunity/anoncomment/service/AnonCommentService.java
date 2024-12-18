package com.adev.vedacommunity.anoncomment.service;

import com.adev.vedacommunity.anoncomment.entity.ActiveAnonComment;
import com.adev.vedacommunity.anoncomment.entity.AnonComment;
import com.adev.vedacommunity.anoncomment.repository.ActiveAnonCommentRepository;
import com.adev.vedacommunity.anoncomment.repository.AnonCommentRepository;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnonCommentService {

    private final AnonCommentRepository anonCommentRepository;
    private final ActiveAnonCommentRepository activeAnonCommentRepository;

    public void createComment(AnonComment AnonComment) {

        if(AnonComment.canCreate()){
            anonCommentRepository.save(AnonComment);
        }
    }

    public ActiveAnonComment readComment(long commentId){
        return activeAnonCommentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("No AnonComment found with id: " + commentId));
    }

    public void updateComment(long commentId, String commentContent, CommunityUserView user){
        anonCommentRepository.findById(commentId).ifPresent(AnonComment -> {
            if(AnonComment.canUpdate(user)){
                AnonComment.update(commentContent);
            }
        });
    }

    public void deleteComment(long commentId,CommunityUserView user){
        anonCommentRepository.findById(commentId).ifPresent(AnonComment ->{
            if(AnonComment.canDelete(user)){
                AnonComment.delete();
            }
        });
    }

    public List<ActiveAnonComment> getCommentList(long articleId){
        List<ActiveAnonComment> commentList = activeAnonCommentRepository.findAllByArticleId(articleId);
        return commentList;
    }

}
