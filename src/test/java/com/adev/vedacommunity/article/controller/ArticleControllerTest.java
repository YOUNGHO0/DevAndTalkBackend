package com.adev.vedacommunity.article.controller;

import com.adev.vedacommunity.article.dto.request.ArticleCreateDto;
import com.adev.vedacommunity.article.dto.request.ArticleDeleteDto;
import com.adev.vedacommunity.article.dto.request.ArticleReadDto;
import com.adev.vedacommunity.article.dto.request.ArticleUpdateDto;
import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.article.mapper.ArticleMapper;
import com.adev.vedacommunity.article.repository.ArticleRepository;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import com.adev.vedacommunity.user.service.UserService;
import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureRestDocs
@Transactional
class ArticleControllerTest {


    private OperationRequestPreprocessor operationRequestPreprocessor = Preprocessors.preprocessRequest(Preprocessors.prettyPrint());
    private OperationResponsePreprocessor operationResponsePreprocessor =  Preprocessors.preprocessResponse(Preprocessors.prettyPrint());

    private MockMvc mockMvc;

    @Autowired
    private CommunityUserRepository communityUserRepository;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private EntityManager em;

    private RequestFieldsSnippet requestFieldsSnippet =  requestFields(
            fieldWithPath("title").type(JsonFieldType.STRING).description("게시판 제목"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("게시판 내용")


    );

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .apply(springSecurity())
                .build();
    }

    private MockHttpSession getSavedUserSession(){


        CommunityUser user = new CommunityUser("test@gmail.com", "testNickname");
        CommunityUser savedUser = communityUserRepository.save(user);

        MockHttpServletRequest request = new MockHttpServletRequest();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(savedUser, null,user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        MockHttpSession mockHttpSession =(MockHttpSession)request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        return mockHttpSession;
    }

    CommunityUser getTestUser(){

        return communityUserRepository.findByEmail("test@gmail.com")
                .orElseThrow(() -> new RuntimeException("No User"));

    }

    @Test
    @DisplayName("정상적인 요청을 하면 글이 작성된다")
    void createArticle() throws Exception {
        getSavedUserSession();

        ArticleCreateDto dto  = new ArticleCreateDto("test title", "test content");
        String json = new Gson().toJson(dto);
        this.mockMvc.perform(post("/api/v1/article")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(getSavedUserSession())
                .content(json)
        ).andExpect(status().isOk())
        .andDo(document("articleCreate/success",
                            operationRequestPreprocessor,
                            operationResponsePreprocessor,
                            requestFieldsSnippet));

    }

    @Test
    @DisplayName("올바른 요청은 게시글을 반환한다.")
    void getArticle() throws Exception {

        getSavedUserSession();

        RequestFieldsSnippet snippet =  requestFields(
                fieldWithPath("articleId").type(JsonFieldType.NUMBER).description("게시판 번호")
        );

        CommunityUser user = getTestUser();
        ArticleCreateDto dto = new ArticleCreateDto("testtitle", "test content");
        Article saved = articleRepository.save(articleMapper.toArticle(dto, user));

        ArticleReadDto readDto = new ArticleReadDto(saved.getId());
        String json = new Gson().toJson(readDto);
        this.mockMvc.perform(get("/api/v1/article")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("articleRead/success",
                        operationRequestPreprocessor,
                        operationResponsePreprocessor
                ));
    }

    @Test
    @DisplayName("올바른 요청은 수정된다")
    void updateArticle() throws Exception {
        MockHttpSession savedUserSession = getSavedUserSession();
        CommunityUser testUser = getTestUser();
        ArticleCreateDto dto = new ArticleCreateDto("testtitle", "test content");
        Article saved = articleRepository.save(articleMapper.toArticle(dto,testUser));

        ArticleUpdateDto updateDto = new ArticleUpdateDto(saved.getId(),"updated","updated");
        String json = new Gson().toJson(updateDto);

        this.mockMvc.perform(patch("/api/v1/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .session(savedUserSession)
                )
                .andExpect(status().isOk())
                .andDo(document("articleUpdate/success",
                        operationRequestPreprocessor,
                        operationResponsePreprocessor
                ));




    }

    @Test
    @DisplayName("올바른 삭제요청이 들어오면 삭제된다")
    void deleteArticle() throws Exception {
        MockHttpSession savedUserSession = getSavedUserSession();
        CommunityUser testUser = getTestUser();
        ArticleCreateDto dto = new ArticleCreateDto("testtitle", "test content");
        Article saved = articleRepository.save(articleMapper.toArticle(dto,testUser));

        ArticleDeleteDto deleteDto = new ArticleDeleteDto(saved.getId());
        String json = new Gson().toJson(deleteDto);

        this.mockMvc.perform(delete("/api/v1/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .session(savedUserSession)
                )
                .andExpect(status().isOk())
                .andDo(document("articleDelete/success",
                        operationRequestPreprocessor,
                        operationResponsePreprocessor
                ));



    }
}