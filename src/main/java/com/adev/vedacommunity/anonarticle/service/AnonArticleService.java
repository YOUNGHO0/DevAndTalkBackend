package com.adev.vedacommunity.anonarticle.service;

import com.adev.vedacommunity.anonarticle.entity.ActiveAnonArticle;
import com.adev.vedacommunity.anonarticle.entity.AnonArticle;
import com.adev.vedacommunity.anonarticle.repository.ActiveAnonArticleRepository;
import com.adev.vedacommunity.anonarticle.repository.AnonArticleRepository;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AnonArticleService {

    private final AnonArticleRepository anonArticleRepository;
    private final ActiveAnonArticleRepository activeAnonArticleRepository;

    public void create(AnonArticle article){

        if(article.canCreate()){
            anonArticleRepository.save(article);
        }

    }

    public ActiveAnonArticle read(long id){
        return activeAnonArticleRepository.findById(id).orElseThrow(() -> new RuntimeException("NoArticle"));
    }

    public void update(long id,String title, String content, CommunityUserView author){
        AnonArticle anonArticle = anonArticleRepository.findById(id).orElseThrow(() -> new RuntimeException("NoArticle"));
        if(anonArticle.canUpdate(anonArticle.getTitle(),anonArticle.getContent(),author)){
            anonArticle.update(title,content);
        }

    }

    public void delete(long id,CommunityUserView author){
        anonArticleRepository.findById(id).ifPresent(anonArticle ->{
            if(anonArticle.canDelete(author)){
                anonArticle.delete();
            }
        });

    }


}
