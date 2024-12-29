package com.adev.vedacommunity.oauth.service;

import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.entity.CommunityUserView;
import com.adev.vedacommunity.user.repository.CommunityUserRepository;
import com.adev.vedacommunity.user.repository.CommunityUserViewRepository;
import com.adev.vedacommunity.user.service.NicknameGenerator;
import com.adev.vedacommunity.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOauthSuccessHandler implements AuthenticationSuccessHandler {

    private final CommunityUserRepository communityUserRepository;
    private final CommunityUserViewRepository communityUserViewRepository;
    private final UserService userService;
    private final NicknameGenerator nicknameGenerator;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = attributes.get("email").toString();

        Optional<CommunityUserView> optionalUser = communityUserViewRepository.findByEmail(email);
        CommunityUserView updatedCommunityUser = optionalUser.isEmpty() ? handleGuest(email): handleUser(optionalUser.get()) ;
        userService.setSession(request, updatedCommunityUser);

        response.sendRedirect("https://devandtalk.xyz");



    }

    private CommunityUserView handleGuest(String email){

        String nickname = nicknameGenerator.generateUniqueNickname();
        CommunityUser communityUser = new CommunityUser(email,nickname);
        communityUserRepository.save(communityUser);
        return communityUserViewRepository.findByEmail(communityUser.getEmail()).get();
    }
    private CommunityUserView handleUser(CommunityUserView communityUserView){
        return communityUserView;
    }

}
