//package com.adev.vedacommunity.anoncomment.controller;
//
//import com.adev.vedacommunity.BaseRestDocsTest;
//import com.adev.vedacommunity.admin.service.AdminService;
//import com.adev.vedacommunity.anonarticle.entity.ActiveAnonArticle;
//import com.adev.vedacommunity.anonarticle.entity.AnonArticle;
//import com.adev.vedacommunity.anonarticle.repository.ActiveAnonArticleRepository;
//import com.adev.vedacommunity.anonarticle.repository.AnonArticleRepository;
//import com.adev.vedacommunity.anoncomment.entity.AnonComment;
//import com.adev.vedacommunity.anoncomment.repository.AnonCommentRepository;
//import com.adev.vedacommunity.article.entity.ActiveArticle;
//import com.adev.vedacommunity.comment.dto.request.*;
//import com.adev.vedacommunity.comment.entity.Comment;
//import com.adev.vedacommunity.comment.repository.CommentRepository;
//import com.adev.vedacommunity.user.entity.CommunityUser;
//import com.adev.vedacommunity.user.entity.CommunityUserView;
//import com.adev.vedacommunity.user.repository.CommunityUserRepository;
//import com.adev.vedacommunity.user.repository.CommunityUserViewRepository;
//import com.google.gson.Gson;
//import jakarta.persistence.EntityManager;
//import jakarta.servlet.http.HttpSession;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@ActiveProfiles("local")
//@Transactional
//class AnonCommentControllerTest extends BaseRestDocsTest {
//
//    @Autowired
//    CommunityUserViewRepository communityUserViewRepository;
//    @Autowired
//    EntityManager em;
//    @Autowired
//    AdminService adminService;
//    @Autowired
//    CommunityUserRepository communityUserRepository;
//    @Autowired
//    AnonArticleRepository articleRepository;
//    @Autowired
//    ActiveAnonArticleRepository activeAnonArticleRepository;
//    @Autowired
//    AnonCommentRepository commentRepository;
//    private MockHttpSession getSavedUserSession(){
//
//
//        CommunityUser user = new CommunityUser("test@gmail.com", "testNickname");
//        CommunityUser savedUser = communityUserRepository.save(user);
//        adminService.registerUser(savedUser.getId());
//        em.flush();
//        em.clear();
//        CommunityUserView communityUserView = communityUserViewRepository.findById(savedUser.getId()).get();
//
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        UsernamePasswordAuthenticationToken authentication =
//                new UsernamePasswordAuthenticationToken(communityUserView, null,communityUserView.getAuthorities());
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//        securityContext.setAuthentication(authentication);
//
//        HttpSession session = request.getSession(true);
//        MockHttpSession mockHttpSession =(MockHttpSession)request.getSession(true);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//
//        return mockHttpSession;
//    }
//
//    private CommunityUserView getTestUser(){
//
//        return communityUserViewRepository.findByEmail("test@gmail.com")
//                .orElseThrow(() -> new RuntimeException("No User"));
//
//
//    }
//
//
//    @Test
//    public void 올바른_요청은_댓글이_작성된다() throws Exception {
//
//        MockHttpSession savedUserSession = getSavedUserSession();
//        CommunityUserView communityUserView = getTestUser();
//        AnonArticle article = new AnonArticle("testAnonArticle","testContent",communityUserView);
//        AnonArticle saved = articleRepository.save(article);
//        CommentCreateRequestDto dto = new CommentCreateRequestDto(saved.getId(), "commentContent");
//
//        String json = new Gson().toJson(dto);
//        this.mockMvc.perform(post("/api/v1/comment")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .session(savedUserSession)
//                        .content(json)
//                ).andExpect(status().isOk())
//                .andDo(document("commentCreate/success",
//                        operationRequestPreprocessor,
//                        operationResponsePreprocessor
//                ));
//
//    }
//
//
//    @Test
//    public void 올바른_요청은_댓글을_반환한다() throws Exception {
//
//        MockHttpSession savedUserSession = getSavedUserSession();
//        CommunityUserView communityUserView = getTestUser();
//        AnonArticle article = new AnonArticle("testAnonArticle","testContent",communityUserView);
//        AnonArticle saved = articleRepository.save(article);
//        ActiveAnonArticle activeAnonArticle = activeAnonArticleRepository.findById(saved.getId()).get();
//        CommentCreateRequestDto dto = new CommentCreateRequestDto(saved.getId(), "commentContent");
//        AnonComment comment = new AnonComment(dto.getCommentContent(),communityUserView,activeAnonArticle);
//        AnonComment savedComment = commentRepository.save(comment);
//        em.flush();
//        em.clear();
//        System.out.println(savedComment.getCommentAuthor().getEmail());
//        CommentReadRequestDto readDto = new CommentReadRequestDto(savedComment.getId());
//        String json = new Gson().toJson(readDto);
//        this.mockMvc.perform(get("/api/v1/comment")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .session(savedUserSession)
//                        .content(json)
//                ).andExpect(status().isOk())
//                .andDo(document("commentGet/success",
//                        operationRequestPreprocessor,
//                        operationResponsePreprocessor
//                ));
//
//    }
//
//
//    @Test
//    public void 올바른_수정_요청은_수정된다() throws Exception {
//
//        MockHttpSession savedUserSession = getSavedUserSession();
//        CommunityUserView communityUserView = getTestUser();
//        AnonArticle article = new AnonArticle("testAnonArticle","testContent",communityUserView);
//        AnonArticle saved = articleRepository.save(article);
//        ActiveAnonArticle activeAnonArticle = activeAnonArticleRepository.findById(saved.getId()).get();
//        CommentCreateRequestDto dto = new CommentCreateRequestDto(saved.getId(), "commentContent");
//        AnonComment comment = new AnonComment(dto.getCommentContent(),communityUserView,activeAnonArticle);
//        AnonComment savedComment = commentRepository.save(comment);
//        em.flush();
//        em.clear();
//        CommentUpdateRequestDto updateDto = new CommentUpdateRequestDto(savedComment.getId(),"changeContent");
//        String json = new Gson().toJson(updateDto);
//        this.mockMvc.perform(patch("/api/v1/comment")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .session(savedUserSession)
//                        .content(json)
//                ).andExpect(status().isOk())
//                .andDo(document("commentGet/success",
//                        operationRequestPreprocessor,
//                        operationResponsePreprocessor
//                ));
//
//        AnonComment updatedcomment = commentRepository.findById(savedComment.getId()).get();
//        Assertions.assertThat(updatedcomment.getCommentContent()).isEqualTo("changeContent");
//
//    }
//
//
//    @Test
//    public void 올바른_삭제요청은_삭제된다() throws Exception {
//
//        MockHttpSession savedUserSession = getSavedUserSession();
//        CommunityUserView communityUserView = getTestUser();
//        AnonArticle article = new AnonArticle("testAnonArticle","testContent",communityUserView);
//        AnonArticle saved = articleRepository.save(article);
//        ActiveAnonArticle activeAnonArticle = activeAnonArticleRepository.findById(saved.getId()).get();
//        CommentCreateRequestDto dto = new CommentCreateRequestDto(saved.getId(), "commentContent");
//        AnonComment comment = new AnonComment(dto.getCommentContent(),communityUserView,activeAnonArticle);
//        AnonComment savedComment = commentRepository.save(comment);
//        em.flush();
//        em.clear();
//        CommentDeleteRequestDto deleteDto = new CommentDeleteRequestDto(savedComment.getId());
//        String json = new Gson().toJson(deleteDto);
//        this.mockMvc.perform(delete("/api/v1/comment")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .session(savedUserSession)
//                        .content(json)
//                ).andExpect(status().isOk())
//                .andDo(document("commentDelete/success",
//                        operationRequestPreprocessor,
//                        operationResponsePreprocessor
//                ));
//
//        AnonComment deletedComment = commentRepository.findById(savedComment.getId()).get();
//        Assertions.assertThat(deletedComment.isDeleted()).isEqualTo(true);
//
//    }
//
//    @Test
//    @DisplayName("올바르게 변환된다")
//    public void commentDtotest() throws Exception {
//
//
//        MockHttpSession savedUserSession = getSavedUserSession();
//        CommunityUserView communityUserView = getTestUser();
//        AnonArticle article = new AnonArticle("testAnonArticle","testContent",communityUserView);
//        AnonArticle saved = articleRepository.save(article);
//        ActiveAnonArticle activeAnonArticle = activeAnonArticleRepository.findById(saved.getId()).get();
//        CommentCreateRequestDto dto = new CommentCreateRequestDto(saved.getId(), "commentContent");
//        AnonComment comment = new AnonComment(dto.getCommentContent(),communityUserView,activeAnonArticle);
//        AnonComment savedComment = commentRepository.save(comment);
//
//        AnonComment childComment = new AnonComment("childComment content",communityUserView,activeAnonArticle,savedComment);
//        commentRepository.save(childComment);
//        em.flush();
//        em.clear();
//        CommentPageRequestDto requestDto = new CommentPageRequestDto(saved.getId());
//        String json = new Gson().toJson(requestDto);
//        this.mockMvc.perform(get("/api/v1/comment/list")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .session(savedUserSession)
//                        .content(json)
//                ).andExpect(status().isOk())
//                .andDo(document("commentGet/success",
//                        operationRequestPreprocessor,
//                        operationResponsePreprocessor
//                ));
//
//
//
//    }
//
//    @Test
//    void getParentCommentOnly() throws Exception {
//        MockHttpSession savedUserSession = getSavedUserSession();
//        CommunityUserView communityUserView = getTestUser();
//        AnonArticle article = new AnonArticle("testAnonArticle", "testContent", communityUserView);
//        AnonArticle saved = articleRepository.save(article);
//
//        CommentCreateRequestDto dto = new CommentCreateRequestDto(saved.getId(), "parentCommentContent");
//        AnonComment comment = new AnonComment(dto.getCommentContent(), communityUserView, activeAnonArticleRepository.findById(saved.getId()).get());
//        commentRepository.save(comment);
//
//        em.flush();
//        em.clear();
//
//        CommentPageRequestDto requestDto = new CommentPageRequestDto(saved.getId());
//        String json = new Gson().toJson(requestDto);
//
//        this.mockMvc.perform(get("/api/v1/comment/list")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .session(savedUserSession)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(1)) // 응답 리스트 크기: 부모 댓글 1개
//                .andExpect(jsonPath("$[0].childCommentList").doesNotExist()) // 첫 번째 댓글의 자식 댓글이 없음
//                .andDo(document("commentGet/parentOnly"));
//    }
//
//    @Test
//    void getParentWithMultipleChildComments() throws Exception {
//        MockHttpSession savedUserSession = getSavedUserSession();
//        CommunityUserView communityUserView = getTestUser();
//        AnonArticle article = new AnonArticle("testAnonArticle", "testContent", communityUserView);
//        AnonArticle saved = articleRepository.save(article);
//        ActiveAnonArticle activeAnonArticle = activeAnonArticleRepository.findById(saved.getId()).get();
//
//        AnonComment parentComment = new AnonComment("parentCommentContent", communityUserView, activeAnonArticle);
//        AnonComment savedParentComment = commentRepository.save(parentComment);
//
//        // 여러 자식 댓글 생성
//        commentRepository.save(new AnonComment("childComment1", communityUserView, activeAnonArticle, savedParentComment));
//        commentRepository.save(new AnonComment("childComment2", communityUserView, activeAnonArticle, savedParentComment));
//        commentRepository.save(new AnonComment("childComment3", communityUserView, activeAnonArticle, savedParentComment));
//
//        em.flush();
//        em.clear();
//
//        CommentPageRequestDto requestDto = new CommentPageRequestDto(saved.getId());
//        String json = new Gson().toJson(requestDto);
//
//        this.mockMvc.perform(get("/api/v1/comment/list")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .session(savedUserSession)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(1))
//                .andExpect(jsonPath("$[0].childCommentList.size()").value(3))
//                .andDo(document("commentGet/multipleChildren",
//                        operationRequestPreprocessor,
//                        operationResponsePreprocessor
//                ));
//    }
//
//
//    @Test
//    public void 삭제된_부모_댓글은_삭제된_덧글로_표신된다() throws Exception {
//
//        MockHttpSession savedUserSession = getSavedUserSession();
//        CommunityUserView communityUserView = getTestUser();
//        AnonArticle article = new AnonArticle("testAnonArticle","testContent",communityUserView);
//        AnonArticle saved = articleRepository.save(article);
//        ActiveAnonArticle activeAnonArticle = activeAnonArticleRepository.findById(saved.getId()).get();
//        CommentCreateRequestDto dto = new CommentCreateRequestDto(saved.getId(), "commentContent");
//        AnonComment comment = new AnonComment(dto.getCommentContent(),communityUserView,activeAnonArticle);
//        AnonComment savedComment = commentRepository.save(comment);
//        commentRepository.save(new AnonComment("childComment1", communityUserView, activeAnonArticle, savedComment));
//        commentRepository.save(new AnonComment("childComment2", communityUserView, activeAnonArticle, savedComment));
//        commentRepository.save(new AnonComment("childComment3", communityUserView, activeAnonArticle, savedComment));
//        commentRepository.save(new AnonComment("childComment3", communityUserView, activeAnonArticle));
//        em.flush();
//        em.clear();
//        CommentDeleteRequestDto deleteDto = new CommentDeleteRequestDto(savedComment.getId());
//        String json = new Gson().toJson(deleteDto);
//        this.mockMvc.perform(delete("/api/v1/comment")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .session(savedUserSession)
//                .content(json)
//        ).andExpect(status().isOk());
//        em.flush();
//        em.clear();
//        CommentPageRequestDto requestDto = new CommentPageRequestDto(activeAnonArticle.getId());
//        String requestJsonn = new Gson().toJson(requestDto);
//        this.mockMvc.perform(get("/api/v1/comment/list")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(requestJsonn)
//                ).andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(2))
//                .andExpect(jsonPath("$[0].commentContent").value("삭제된 댓글입니다"))
//                .andExpect(jsonPath("$[0].author").isEmpty())
//                .andDo(document("commentDelete/commentDeleted",
//                        operationRequestPreprocessor,
//                        operationResponsePreprocessor
//                ));
//
//
//
//    }
//
//    @Test
//    public void 자식댓글은_삭제하면_보이지_않는다() throws Exception {
//
//        MockHttpSession savedUserSession = getSavedUserSession();
//        CommunityUserView communityUserView = getTestUser();
//        AnonArticle article = new AnonArticle("testAnonArticle","testContent",communityUserView);
//        AnonArticle saved = articleRepository.save(article);
//        ActiveAnonArticle activeAnonArticle = activeAnonArticleRepository.findById(saved.getId()).get();
//        CommentCreateRequestDto dto = new CommentCreateRequestDto(saved.getId(), "commentContent");
//        AnonComment comment = new AnonComment(dto.getCommentContent(),communityUserView,activeAnonArticle);
//        AnonComment savedComment = commentRepository.save(comment);
//        commentRepository.save(new AnonComment("childComment1", communityUserView, activeAnonArticle, savedComment));
//        AnonComment targetComment = commentRepository.save(new AnonComment("childComment2", communityUserView, activeAnonArticle, savedComment));
//        commentRepository.save(new AnonComment("childComment3", communityUserView, activeAnonArticle, savedComment));
//        commentRepository.save(new AnonComment("childComment3", communityUserView, activeAnonArticle));
//        em.flush();
//        em.clear();
//        CommentDeleteRequestDto deleteDto = new CommentDeleteRequestDto(targetComment.getId());
//        String json = new Gson().toJson(deleteDto);
//        this.mockMvc.perform(delete("/api/v1/comment")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .session(savedUserSession)
//                .content(json)
//        ).andExpect(status().isOk());
//        em.flush();
//        em.clear();
//        CommentPageRequestDto requestDto = new CommentPageRequestDto(activeAnonArticle.getId());
//        String requestJsonn = new Gson().toJson(requestDto);
//        this.mockMvc.perform(get("/api/v1/comment/list")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(requestJsonn)
//                ).andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(2))
//                .andExpect(jsonPath("$..childCommentList[?(@.commentContent == 'childComment2')]").doesNotExist())
//                .andDo(document("commentDelete/childCommentDeleted",
//                        operationRequestPreprocessor,
//                        operationResponsePreprocessor
//                ));
//
//
//    }
//
//    @Test
//    public void 자식_덧글은_변경_가능하다() throws Exception {
//
//        MockHttpSession savedUserSession = getSavedUserSession();
//        CommunityUserView communityUserView = getTestUser();
//        AnonArticle article = new AnonArticle("testAnonArticle","testContent",communityUserView);
//        AnonArticle saved = articleRepository.save(article);
//        ActiveAnonArticle activeAnonArticle = activeAnonArticleRepository.findById(saved.getId()).get();
//        CommentCreateRequestDto dto = new CommentCreateRequestDto(saved.getId(), "commentContent");
//        AnonComment comment = new AnonComment(dto.getCommentContent(),communityUserView,activeAnonArticle);
//        AnonComment savedComment = commentRepository.save(comment);
//        commentRepository.save(new AnonComment("childComment1", communityUserView, activeAnonArticle, savedComment));
//        AnonComment targetComment = commentRepository.save(new AnonComment("childComment2", communityUserView, activeAnonArticle, savedComment));
//        commentRepository.save(new AnonComment("childComment3", communityUserView, activeAnonArticle, savedComment));
//        commentRepository.save(new AnonComment("childComment3", communityUserView, activeAnonArticle));
//        em.flush();
//        em.clear();
//        CommentUpdateRequestDto update = new CommentUpdateRequestDto(targetComment.getId(),"changedContent2");
//        String json = new Gson().toJson(update);
//        this.mockMvc.perform(patch("/api/v1/comment")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .session(savedUserSession)
//                .content(json)
//        ).andExpect(status().isOk());
//        em.flush();
//        em.clear();
//        CommentPageRequestDto requestDto = new CommentPageRequestDto(activeAnonArticle.getId());
//        String requestJsonn = new Gson().toJson(requestDto);
//        this.mockMvc.perform(get("/api/v1/comment/list")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(requestJsonn)
//                ).andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(2))
//                .andExpect(jsonPath("$..childCommentList[?(@.commentContent == 'childComment2')]").doesNotExist())
//                .andExpect(jsonPath("$..childCommentList[?(@.commentContent == 'changedContent2')]").exists())
//                .andDo(document("commentUpdate/childCommentUpdated",
//                        operationRequestPreprocessor,
//                        operationResponsePreprocessor
//                ));
//
//    }
//
//
//    @Test
//    public void 부모_덧글도_변경_가능하다() throws Exception {
//
//        MockHttpSession savedUserSession = getSavedUserSession();
//        CommunityUserView communityUserView = getTestUser();
//        AnonArticle article = new AnonArticle("testAnonArticle","testContent",communityUserView);
//        AnonArticle saved = articleRepository.save(article);
//        ActiveAnonArticle activeAnonArticle = activeAnonArticleRepository.findById(saved.getId()).get();
//        CommentCreateRequestDto dto = new CommentCreateRequestDto(saved.getId(), "commentContent");
//        AnonComment comment = new AnonComment(dto.getCommentContent(),communityUserView,activeAnonArticle);
//        AnonComment targetComment = commentRepository.save(comment);
//        commentRepository.save(new AnonComment("childComment1", communityUserView, activeAnonArticle, targetComment));
//        commentRepository.save(new AnonComment("childComment2", communityUserView, activeAnonArticle, targetComment));
//        commentRepository.save(new AnonComment("childComment3", communityUserView, activeAnonArticle, targetComment));
//        commentRepository.save(new AnonComment("childComment3", communityUserView, activeAnonArticle));
//        em.flush();
//        em.clear();
//        CommentUpdateRequestDto update = new CommentUpdateRequestDto(targetComment.getId(),"parentCommentChanged");
//        String json = new Gson().toJson(update);
//        this.mockMvc.perform(patch("/api/v1/comment")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .session(savedUserSession)
//                .content(json)
//        ).andExpect(status().isOk());
//        em.flush();
//        em.clear();
//        CommentPageRequestDto requestDto = new CommentPageRequestDto(activeAnonArticle.getId());
//        String requestJsonn = new Gson().toJson(requestDto);
//        this.mockMvc.perform(get("/api/v1/comment/list")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(requestJsonn))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[?(@.commentContent == 'parentCommentContent')]").doesNotExist()) // 'parentCommentContent'가 존재하지 않음을 확인
//                .andExpect(jsonPath("$[0].commentContent").value("parentCommentChanged")) // 첫 번째 댓글이 'parentCommentChanged'인지 확인
//                .andDo(document("commentUpdate/parentCommentUpdated",
//                        operationRequestPreprocessor,
//                        operationResponsePreprocessor
//                ));
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}