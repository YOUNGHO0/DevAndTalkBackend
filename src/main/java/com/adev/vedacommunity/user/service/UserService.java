package com.adev.vedacommunity.user.service;

import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final CommunityUserRepository communityUserRepository;



    public void setSession(HttpServletRequest request, CommunityUserView communityUser){

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(communityUser, null, communityUser.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

    public void deleteUser(CommunityUserView user){

        CommunityUser communityUser = communityUserRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("No User"));
        communityUser.delete();


    }



}
