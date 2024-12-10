package com.adev.vedacommunity.user.service;

import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    CommunityUserRepository communityUserRepository;



    public void setSession(HttpServletRequest request, CommunityUser communityUser){

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(communityUser, null, communityUser.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }



}
