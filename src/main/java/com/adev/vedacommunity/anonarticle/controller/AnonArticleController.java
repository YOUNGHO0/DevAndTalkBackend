package com.adev.vedacommunity.anonarticle.controller;

import com.adev.vedacommunity.anonarticle.dto.request.AnonArticleCreateRequestDto;
import com.adev.vedacommunity.anonarticle.dto.request.AnonArticleDeleteRequestDto;
import com.adev.vedacommunity.anonarticle.dto.request.AnonArticleReadRequestDto;
import com.adev.vedacommunity.anonarticle.dto.request.AnonArticleUpdateRequestDto;
import com.adev.vedacommunity.anonarticle.dto.response.AnonArticleReadResponseDto;
import com.adev.vedacommunity.anonarticle.entity.ActiveAnonArticle;
import com.adev.vedacommunity.anonarticle.entity.AnonArticle;
import com.adev.vedacommunity.anonarticle.mapper.AnonArticleMapper;
import com.adev.vedacommunity.anonarticle.repository.ActiveAnonArticleRepository;
import com.adev.vedacommunity.anonarticle.repository.AnonArticleRepository;
import com.adev.vedacommunity.anonarticle.service.AnonArticleService;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/anonArticle")
public class AnonArticleController {

    private final AnonArticleService anonArticleService;
    private final AnonArticleMapper anonArticleMapper;
    private final AnonArticleRepository anonArticleRepository;
    private final ActiveAnonArticleRepository activeAnonArticleRepository;

    @PostMapping
    public ResponseEntity createAnonArticle(@RequestBody AnonArticleCreateRequestDto requestDto , @AuthenticationPrincipal CommunityUserView communityUser){
        AnonArticle anonArticle = anonArticleMapper.toAnonArticle(requestDto, communityUser);
        anonArticleService.create(anonArticle);
        return ResponseEntity.ok().build();
    };

    @GetMapping
    public ResponseEntity readAnonArticle(@RequestBody AnonArticleReadRequestDto requestDto ){
        ActiveAnonArticle read = anonArticleService.read(requestDto.getAnonArticleId());
        AnonArticleReadResponseDto readDto = anonArticleMapper.toReadDto(read);

        return ResponseEntity.ok(readDto);
    };

    @PatchMapping
    public ResponseEntity updateAnonArticle(@RequestBody AnonArticleUpdateRequestDto requestDto, @AuthenticationPrincipal CommunityUserView user){
        anonArticleService.update(requestDto.getId(),requestDto.getTitle(),requestDto.getContent(),user);
        return ResponseEntity.ok().build();
    };

    @DeleteMapping
    public ResponseEntity deleteAnonarticle(@RequestBody AnonArticleDeleteRequestDto deleteRequestDto,  @AuthenticationPrincipal CommunityUserView user){
        anonArticleService.delete(deleteRequestDto.getAnonArticleId(),user);
        return ResponseEntity.ok().build();
    };

    @GetMapping("/{id}")
    public ResponseEntity getArticle(@PathVariable long id) {
        ActiveAnonArticle anonArticle = anonArticleService.read(id); // id를 사용하여 데이터 조회

        return ResponseEntity.ok(anonArticleMapper.toReadDto(anonArticle));
    }

    @GetMapping("/list")
    public ResponseEntity getArticle(Pageable pageable){
        Page<ActiveAnonArticle> list = activeAnonArticleRepository.getArticleList(pageable);
        return ResponseEntity.ok().body(anonArticleMapper.toAnonArticleReadResponseDto(list));
    }
}
