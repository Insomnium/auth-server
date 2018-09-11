package ru.coddebattle.oidc.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http.requestMatchers()
            .antMatchers("/login", "/oauth/authorize", "/oauth2/authorization/**", "/login/oauth2/code/**")
            .and()
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .formLogin().permitAll()
            .and()
            .oauth2Login()
                .permitAll()
                .userInfoEndpoint().userService(new DefaultOAuth2UserService() {
            @Override
            public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
                OAuth2User oauthUser = super.loadUser(userRequest);
                Map<String, Object> userAttributes = oauthUser.getAttributes();
                // github
                String userName = (String) userAttributes.get("login");
                String avatarUri = (String) userAttributes.get("avatar_url");
                String email = (String) userAttributes.get("email");
                return oauthUser;
            }
        })
        .oidcUserService(new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
                OidcUser oidcUser = super.loadUser(userRequest);
                Map<String, Object> attributes = oidcUser.getAttributes();
                String email = (String) attributes.get("email");
                String givenName = (String) attributes.get("given_name");
                return oidcUser;
            }
        })
        ;
    } // @formatter:on

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
        auth.inMemoryAuthentication()
            .withUser("john")
            .password(passwordEncoder().encode("123"))
            .roles("USER");
    } // @formatter:on

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
