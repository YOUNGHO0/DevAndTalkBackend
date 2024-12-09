package com.adev.vedacommunity.oauth.service;

import com.adev.vedacommunity.user.entity.CommunityUser;
import com.adev.vedacommunity.user.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final UserService userService;
    private final NicknameGenerator nicknameGenerator;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = attributes.get("email").toString();

        Optional<CommunityUser> optionalUser = userRepository.findByEmail(email);
        CommunityUser updatedCommunityUser = optionalUser.isEmpty() ? handleGuest(email): handleUser(optionalUser.get()) ;
        userService.setSession(request, updatedCommunityUser);

    }

    private CommunityUser handleGuest(String email){

        String nickname = nicknameGenerator.generateUniqueNickname();
        CommunityUser communityUser = new CommunityUser(email,nickname);
        userRepository.save(communityUser);
        return communityUser;
    }
    private CommunityUser handleUser(CommunityUser communityUser){
        return communityUser;
    }

}
