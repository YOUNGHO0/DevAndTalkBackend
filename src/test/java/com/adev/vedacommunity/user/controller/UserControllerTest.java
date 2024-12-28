package com.adev.vedacommunity.user.controller;

import com.adev.vedacommunity.BaseRestDocsTest;
import com.adev.vedacommunity.admin.service.AdminService;
import com.adev.vedacommunity.anonarticle.entity.ActiveAnonArticle;
import com.adev.vedacommunity.anonarticle.entity.AnonArticle;
import com.adev.vedacommunity.anonarticle.repository.ActiveAnonArticleRepository;
import com.adev.vedacommunity.anonarticle.repository.AnonArticleRepository;
import com.adev.vedacommunity.anoncomment.dto.request.AnonCommentPageRequestDto;
import com.adev.vedacommunity.anoncomment.entity.AnonComment;
import com.adev.vedacommunity.anoncomment.repository.AnonCommentRepository;
import com.adev.vedacommunity.article.entity.ActiveArticle;
import com.adev.vedacommunity.article.entity.Article;
import com.adev.vedacommunity.article.repository.ActiveArticleRepository;
import com.adev.vedacommunity.article.repository.ArticleRepository;
import com.adev.vedacommunity.comment.dto.request.CommentPageRequestDto;
import com.adev.vedacommunity.comment.entity.Comment;
import com.adev.vedacommunity.comment.repository.CommentRepository;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import com.adev.vedacommunity.user.repository.CommunityUserViewRepository;
import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ActiveProfiles("local")
class UserControllerTest extends BaseRestDocsTest {

    @Autowired
    private CommunityUserViewRepository communityUserViewRepository;
    @Autowired
    private CommunityUserRepository communityUserRepository;
    @Autowired
    private AdminService adminService;
    @Autowired
    private EntityManager em;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ActiveArticleRepository activeArticleRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    AnonArticleRepository anonArticleRepository;
    @Autowired
    ActiveAnonArticleRepository activeAnonArticleRepository;
    @Autowired
    AnonCommentRepository anonCommentRepository;
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

    private CommunityUserView getTestUser(){

        return communityUserViewRepository.findByEmail("test@gmail.com")
                .orElseThrow(() -> new RuntimeException("No User"));


    }


    @Test
    public void 사용자가_올바르게_삭제된다() throws Exception {

        MockHttpSession savedUserSession = getSavedUserSession();

        CommunityUserView testUser = getTestUser();
        Article article = new Article("test title", "test content", testUser);
        articleRepository.save(article);

        em.flush();
        em.clear();
        this.mockMvc.perform(delete("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .session(savedUserSession)
                ).andExpect(status().isOk())
                .andDo(document("userDelete/success",
                        operationRequestPreprocessor,
                        operationResponsePreprocessor
                ));

        em.flush();
        em.clear();

        this.mockMvc.perform(get("/api/v1/article/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .session(savedUserSession)
                ).andExpect(status().isOk())
                .andDo(document("userDelete/afterDelete",
                        operationRequestPreprocessor,
                        operationResponsePreprocessor
                ));
    }

    @Test
    public void 사용자의_모든_기록이_삭제된다() throws Exception {

        MockHttpSession savedUserSession = getSavedUserSession();

        CommunityUserView testUser = getTestUser();
        Article article = new Article("test title", "test content", testUser);
        articleRepository.save(article);
        ActiveArticle activeArticle = activeArticleRepository.findById(article.getId()).orElseThrow(() -> new RuntimeException("no"));

        Comment comment = new Comment("commentcontent",testUser,activeArticle);
        commentRepository.save(comment);

        AnonArticle anonArticle = new AnonArticle("anontitle","anoncontent",testUser);
        AnonArticle savedAnonArticle = anonArticleRepository.save(anonArticle);
        ActiveAnonArticle activeAnonArticle = activeAnonArticleRepository.findById(savedAnonArticle.getId()).orElseThrow(() -> new RuntimeException("no"));

        AnonComment anonComment = new AnonComment("anonComment",testUser,activeAnonArticle);
        AnonComment savedAnonComment = anonCommentRepository.save(anonComment);

        em.flush();
        em.clear();
        this.mockMvc.perform(delete("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .session(savedUserSession)
                ).andExpect(status().isOk());

        em.flush();
        em.clear();

//        this.mockMvc.perform(get("/api/v1/article/list")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .session(savedUserSession)
//                ).andExpect(status().isOk())
//                .andExpect(jsonPath("$.content.size()").value(0));
//
//        this.mockMvc.perform(get("/api/v1/anonArticle/list")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .session(savedUserSession)
//                ).andExpect(status().isOk())
//                .andExpect(jsonPath("$.content.size()").value(0));


        em.flush();
        em.clear();
        CommentPageRequestDto dto = new CommentPageRequestDto(activeArticle.getId());
        String json = new Gson().toJson(dto);
        this.mockMvc.perform(get("/api/v1/comment/list/"+activeArticle.getId() )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .session(savedUserSession)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty()); // 배열이 비어 있는지 확인

        AnonCommentPageRequestDto anondto = new AnonCommentPageRequestDto(activeAnonArticle.getId());
        String anonjson = new Gson().toJson(anondto);

//        this.mockMvc.perform(get("/api/v1/anonComment/list/"+activeAnonArticle.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(anonjson)
//                        .session(savedUserSession)
//                ).andExpect(status().isOk())
//                .andExpect(jsonPath("$").isEmpty());




    }



}