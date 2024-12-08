package com.adev.vedacommunity.security;
import com.adev.vedacommunity.oauth.service.CustomOauthFailureHandler;
import com.adev.vedacommunity.oauth.service.CustomOauthSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOauthSuccessHandler oauthSuccessHandler;
    private final CustomOauthFailureHandler oauthFailureHandler;


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .csrf(CsrfConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .headers(headerConfig ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable())
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/api/v1/*").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/*").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()  // GET 요청은 인증 없이 접근 가능
                        .anyRequest().authenticated()) // 나머지 요청은 인증 필요
                .oauth2Login(configurer -> configurer.successHandler(oauthSuccessHandler)
                        .failureHandler(oauthFailureHandler)
                )
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(new EntryPointUnauthorizedHandler(new ObjectMapper())));

        return http.build();


    }
}
