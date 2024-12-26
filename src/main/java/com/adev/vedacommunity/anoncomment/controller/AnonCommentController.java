package com.adev.vedacommunity.anoncomment.controller;

import com.adev.vedacommunity.anonarticle.entity.ActiveAnonArticle;
import com.adev.vedacommunity.anonarticle.entity.AnonArticle;
import com.adev.vedacommunity.anonarticle.repository.ActiveAnonArticleRepository;
import com.adev.vedacommunity.anonarticle.service.AnonArticleService;
import com.adev.vedacommunity.anoncomment.dto.read.AnonCommentDto;
import com.adev.vedacommunity.anoncomment.dto.read.AnonCommentReadResponseDto;
import com.adev.vedacommunity.anoncomment.dto.request.*;
import com.adev.vedacommunity.anoncomment.entity.ActiveAnonComment;
import com.adev.vedacommunity.anoncomment.entity.AnonComment;
import com.adev.vedacommunity.anoncomment.mapper.AnonCommentMapper;
import com.adev.vedacommunity.anoncomment.repository.ActiveAnonCommentRepository;
import com.adev.vedacommunity.anoncomment.repository.AnonCommentRepository;
import com.adev.vedacommunity.anoncomment.service.AnonCommentService;
import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.article.service.ArticleService;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.mapper.CommunityUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/anonComment")
@RequiredArgsConstructor
public class AnonCommentController {

    private final AnonCommentService anonCommentService;
    private final AnonCommentMapper anonCommentMapper;
    private final AnonArticleService articleService;
    private final ActiveAnonCommentRepository activeAnonCommentRepository;
    private final AnonCommentRepository anonCommentRepository;
    private final CommunityUserMapper communityUserMapper;
    private final ActiveAnonArticleRepository activeAnonArticleRepository;

    @PostMapping
    public ResponseEntity creatComment(@RequestBody AnonCommentCreateRequestDto dto, @AuthenticationPrincipal CommunityUserView user){

        ActiveAnonArticle article = articleService.read(dto.getArticleId());
        AnonComment AnonComment = anonCommentMapper.toComment(dto.getCommentContent(), user, article);
        anonCommentService.createComment(AnonComment);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/child")
    public ResponseEntity createChildComment(@RequestBody AnonChildCreateRequestDto dto, @AuthenticationPrincipal CommunityUserView user){

        AnonComment parentComment =  anonCommentRepository.findById(dto.getParentId()).orElseThrow(() -> new RuntimeException("No AnonComment found"));
        AnonComment childComment = anonCommentMapper.toChildComment(dto, parentComment, user, parentComment.getArticle());
        anonCommentService.createComment(childComment);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/{id}")
    public ResponseEntity getChildCommentList(@PathVariable Long id, @AuthenticationPrincipal CommunityUserView requestUser){

        List<ActiveAnonComment> commentList = anonCommentService.getCommentList(id);
        ActiveAnonArticle activeAnonArticle = activeAnonArticleRepository.findById(id).orElseThrow(() -> new RuntimeException("No AnonArticle found"));
        CommunityUserView anonArticleAuthor = activeAnonArticle.getAuthor();

        List<AnonCommentDto> anonCommentDtoList = makeCommentDtos(commentList,requestUser,anonArticleAuthor);
        return ResponseEntity.ok().body(anonCommentDtoList);
    }

    private List<AnonCommentDto> makeCommentDtos(List<ActiveAnonComment> commentList, CommunityUserView requestUser, CommunityUserView anonArticleAuthor) {



        Map<Long, AnonCommentDto> parentCommentMap = new HashMap<>();
        Map<Long, List<AnonCommentDto>> childComment = new HashMap<>();

        for(ActiveAnonComment AnonComment : commentList){

            boolean author = false;
            boolean isAnonArticleUser = false;
            if(anonArticleAuthor.equals(AnonComment.getCommentAuthor())) isAnonArticleUser = true;
            if(requestUser.equals(AnonComment.getCommentAuthor())) author = true;
            if(AnonComment.getParentCommentId() == null){
                AnonCommentDto parent = new AnonCommentDto(
                        AnonComment.getId(),
                        AnonComment.getCommentContent(),
                        AnonComment.getCreatedDate(),
                        author,
                        isAnonArticleUser
                        );
                parentCommentMap.put(AnonComment.getId(),parent);
            }
            else{
                List<AnonCommentDto> comList = childComment.get(AnonComment.getParentCommentId());
                if (comList == null) {
                    comList = new ArrayList<>();
                    childComment.put(AnonComment.getParentCommentId(), comList); // 맵에 새 리스트를 저장
                }
                comList.add(
                        new AnonCommentDto(AnonComment.getId(),
                                AnonComment.getCommentContent(),
                                AnonComment.getCreatedDate(),
                                author,
                                isAnonArticleUser
                        ));

            }
        }

        List<AnonCommentDto> anonCommentDtoList = new ArrayList<>();


        Set<Long> childKeys = childComment.keySet();
        for(Long key : childKeys){

            if(parentCommentMap.containsKey(key)){
                AnonCommentDto parentAnonCommentDto = parentCommentMap.get(key);
                List<AnonCommentDto> childCommentList = childComment.get(key);
                parentAnonCommentDto.setChildCommentList(childCommentList);
                parentCommentMap.remove(key);
                anonCommentDtoList.add(parentAnonCommentDto);
            }
            else{
                AnonCommentDto parentMockDto = new AnonCommentDto(key,"삭제된 댓글입니다",null,false,false);
                List<AnonCommentDto> childCommentList = childComment.get(key);
                parentMockDto.setChildCommentList(childCommentList);
                anonCommentDtoList.add(parentMockDto);
            }
        }
        for( AnonCommentDto parentComment :parentCommentMap.values()){
            anonCommentDtoList.add(parentComment);
        }
       anonCommentDtoList.sort(Comparator.comparing(AnonCommentDto::getId));

        return anonCommentDtoList;
    }

    @GetMapping
    public ResponseEntity getComments(@RequestBody AnonCommentReadRequestDto dto){
        ActiveAnonComment AnonComment = anonCommentService.readComment(dto.getCommentId());
        AnonCommentReadResponseDto anonCommentReadResponseDto = anonCommentMapper.toDto(AnonComment);
        return ResponseEntity.ok(anonCommentReadResponseDto);
    }
    @PatchMapping
    public ResponseEntity updateComment(@RequestBody AnonCommentUpdateRequestDto dto, @AuthenticationPrincipal CommunityUserView user){

        anonCommentService.updateComment(dto.getCommentId(),dto.getContent(),user);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping
    public ResponseEntity deleteComment(@RequestBody AnonCommentDeleteRequestDto dto, @AuthenticationPrincipal CommunityUserView user){
        anonCommentService.deleteComment(dto.getCommentId(),user);
        return ResponseEntity.ok().build();
    }

}
