package com.adev.vedacommunity.comment.repository;

import com.adev.vedacommunity.BaseRestDocsTest;
import com.adev.vedacommunity.admin.service.AdminService;
import com.adev.vedacommunity.article.repository.ActiveArticleRepository;
import com.adev.vedacommunity.article.repository.ArticleRepository;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import com.adev.vedacommunity.user.repository.CommunityUserViewRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles("local")
class ActiveAnonCommentRepositoryTest extends BaseRestDocsTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommunityUserRepository communityUserRepository;
    @Autowired
    private CommunityUserViewRepository communityUserViewRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ActiveArticleRepository activeArticleRepository;

    private RequestFieldsSnippet requestFieldsSnippet =  requestFields(
            fieldWithPath("title").type(JsonFieldType.STRING).description("게시판 제목"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("게시판 내용")


    );
    @Transactional
    protected MockHttpSession getSavedUserSession(){


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
    @Transactional
    @DisplayName("올바르게 작성된 댓글을 가져온다")
    public void testCommentList() throws Exception {

//        MockHttpSession savedUserSession = getSavedUserSession();
//        CommunityUserView communityUserView = communityUserViewRepository.findByEmail("test@gmail.com").get();
//        Article article = new Article("testArticle","content", communityUserView);
//        Article savedArticle = articleRepository.save(article);
//        ActiveArticle activeArticle = activeArticleRepository.findById(savedArticle.getId()).get();
//        Comment comment = new Comment("comment content", communityUserView,activeArticle);
//        Comment savedParentComment = commentRepository.save(comment);
//        Comment childComment = new Comment("child comment",communityUserView,activeArticle,savedParentComment);
//        commentRepository.save(childComment);
//
//
//        this.mockMvc.perform(post("/api/v1/article")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .session(getSavedUserSession())
//                        .content(json)
//                ).andExpect(status().isOk())
//                .andDo(document("articleCreate/success",
//                        operationRequestPreprocessor,
//                        operationResponsePreprocessor));
//
//        ChildCommentCreateRequestDto childDto = new ChildCommentCreateRequestDto()


    }

}