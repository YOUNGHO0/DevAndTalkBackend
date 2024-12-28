package com.adev.vedacommunity.admin.controller;

import com.adev.vedacommunity.BaseRestDocsTest;
import com.adev.vedacommunity.admin.entity.AdminCommunityUser;
import com.adev.vedacommunity.admin.repository.AdminCommunityRepository;
import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import com.adev.vedacommunity.user.repository.CommunityUserViewRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("local")
class AdminControllerTest extends BaseRestDocsTest {

    @Autowired
    CommunityUserRepository communityUserRepository;
    @Autowired
    CommunityUserViewRepository communityUserViewRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    EntityManager em;
    MockHttpSession setAdminUser(){
        AdminCommunityUser adminCommunityUser = new AdminCommunityUser("admin","test@gmail.com","lee");
        AdminCommunityUser savedUser = communityUserRepository.save(adminCommunityUser);

        MockHttpServletRequest request = new MockHttpServletRequest();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(savedUser, null,savedUser.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        MockHttpSession mockHttpSession =(MockHttpSession)request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        return mockHttpSession;
    }

    public void test() throws Exception {

        MockHttpSession session = setAdminUser();

        for(int i =0; i<20; i++){
            CommunityUser user = new CommunityUser("test"+i ,"tsetNickanem"+ i);
            communityUserRepository.save(user);
        }
        em.flush();
        System.out.println(communityUserViewRepository.findAll().size());
        System.out.println(communityUserRepository.findAll().size());

        this.mockMvc.perform(get("/api/v1/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .session(session)
                )
                .andExpect(status().isOk())
                .andDo(document(" userList/success",
                        operationRequestPreprocessor,
                        operationResponsePreprocessor
                ));


    }


    @Test
    @DisplayName("쿼리 테스트")
    public void test2() throws Exception {

        CommunityUser user = new AdminCommunityUser("test@gmail.com","hello","lee");
        communityUserRepository.save(user);

        em.flush();
        em.clear();

    }


}

