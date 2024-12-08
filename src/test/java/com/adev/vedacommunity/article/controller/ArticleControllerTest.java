package com.adev.vedacommunity.article.controller;

import com.adev.vedacommunity.article.dto.ArticleCreateDto;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.repository.UserRepository;
import com.adev.vedacommunity.user.service.UserService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import org.assertj.core.api.Assertions;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs
class ArticleControllerTest {


    private OperationRequestPreprocessor operationRequestPreprocessor = Preprocessors.preprocessRequest(Preprocessors.prettyPrint());
    private OperationResponsePreprocessor operationResponsePreprocessor =  Preprocessors.preprocessResponse(Preprocessors.prettyPrint());

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    private RequestFieldsSnippet requestFieldsSnippet =  requestFields(
            fieldWithPath("title").type(JsonFieldType.STRING).description("게시판 제목"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("게시판 내용")


    );

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                //.apply(springSecurity())
                .build();
    }

    private MockHttpSession getSavedUserSession(){


        CommunityUser user = new CommunityUser("test@gmail.com", "testNickname");
        CommunityUser savedUser = userRepository.save(user);

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

    @Test
    @DisplayName("정상적인 요청을 하면 글이 작성된다")
    void createArticle() throws Exception {

        Assertions.assertThat(1).isEqualTo(1);

        ArticleCreateDto dto  = new ArticleCreateDto("test title", "test content");
        String json = new Gson().toJson(dto);
        this.mockMvc.perform(post("/api/v1/article/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk()).andDo(
                document("test/hello",
                        operationRequestPreprocessor,
                        operationResponsePreprocessor,
                        requestFieldsSnippet)
        );

    }

    @Test
    void getArticle() {
    }

    @Test
    void updateArticle() {
    }

    @Test
    void deleteArticle() {
    }
}