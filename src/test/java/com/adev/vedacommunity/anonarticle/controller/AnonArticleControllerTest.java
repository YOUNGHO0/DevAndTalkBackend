package com.adev.vedacommunity.anonarticle.controller;

import com.adev.vedacommunity.BaseRestDocsTest;
import com.adev.vedacommunity.admin.service.AdminService;
import com.adev.vedacommunity.anonarticle.dto.request.AnonArticleCreateRequestDto;
import com.adev.vedacommunity.anonarticle.dto.request.AnonArticleReadRequestDto;
import com.adev.vedacommunity.anonarticle.entity.AnonArticle;
import com.adev.vedacommunity.anonarticle.repository.AnonArticleRepository;
import com.adev.vedacommunity.article.dto.request.ArticleReadDto;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import com.adev.vedacommunity.user.repository.CommunityUserViewRepository;
import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ActiveProfiles("local")
class AnonArticleControllerTest extends BaseRestDocsTest {

    @Autowired
    CommunityUserRepository communityUserRepository;

    @Autowired
    CommunityUserViewRepository communityUserViewRepository;

    @Autowired
    AnonArticleRepository anonArticleRepository;

    @Autowired
    AdminService adminService;

    @Autowired
    EntityManager em;


    private MockHttpSession getSavedUserSession(){


        CommunityUser user = new CommunityUser("test@gmail.com", "testNickname");
        CommunityUser savedUser = communityUserRepository.save(user);
        adminService.registerUser(savedUser.getId());
        em.flush();
        em.clear();
        CommunityUserView communityUserView = communityUserViewRepository.findById(savedUser.getId()).get();

        MockHttpServletRequest request = new MockHttpServletRequest();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(communityUserView, null,communityUserView.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        MockHttpSession mockHttpSession =(MockHttpSession)request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        return mockHttpSession;
    }


    @Test
    @DisplayName("올바른 요청은 등록 가능하다.")
    public void successRequset() throws Exception {

        MockHttpSession savedUserSession = getSavedUserSession();
        AnonArticleCreateRequestDto dto = new AnonArticleCreateRequestDto("testAnonArticle","content");

        String json = new Gson().toJson(dto);

        this.mockMvc.perform(post("/api/v1/anonArticle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .session(savedUserSession)
                )
                .andExpect(status().isOk())
                .andDo(document("anonArticle/success",
                        operationRequestPreprocessor,
                        operationResponsePreprocessor
                ));
    }


    @Test
    @DisplayName("get 요청시 올바르게 반환한다")
    public void getSuccessRequest() throws Exception {

        MockHttpSession savedUserSession = getSavedUserSession();
        CommunityUserView communityUserView = communityUserViewRepository.findByEmail("test@gmail.com").orElseThrow(() -> new RuntimeException("no"));
        AnonArticle anonArticle = new AnonArticle("test", "content,",communityUserView);
        AnonArticle saved = anonArticleRepository.save(anonArticle);

        em.flush();
        em.clear();

        AnonArticleReadRequestDto dto = new AnonArticleReadRequestDto(saved.getId());
        String json = new Gson().toJson(dto);

        this.mockMvc.perform(get("/api/v1/anonArticle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .session(savedUserSession)
                )
                .andExpect(status().isOk())
                .andDo(document("anonArticle/get/success",
                        operationRequestPreprocessor,
                        operationResponsePreprocessor
                ));
    }


}